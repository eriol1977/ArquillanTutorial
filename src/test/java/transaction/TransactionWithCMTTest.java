/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import transaction.bean.MyBeanWithCMT;
import transaction.bean.MyBeanRemote;
import transaction.entity.ContaCorrente;
import transaction.exception.MyException;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TransactionWithCMTTest
{

    @EJB
    MyBeanRemote myBean;

    @Deployment
    public static JavaArchive createDeployment()
    {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(MyBeanWithCMT.class, MyBeanRemote.class, ContaCorrente.class, MyException.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }

    @Test
    public void test() throws Exception
    {
        ContaCorrente conta1 = new ContaCorrente(1, 10);
        ContaCorrente conta2 = new ContaCorrente(2, 10);

        myBean.incluirContaCorrente(conta1);
        myBean.incluirContaCorrente(conta2);

        conta1 = myBean.selecionarContaCorrente(1);
        Assert.assertEquals(10, conta1.getSaldo().intValue());
        conta2 = myBean.selecionarContaCorrente(2);
        Assert.assertEquals(10, conta2.getSaldo().intValue());

        myBean.efetuarTransferencia(conta1, conta2, 5);

        conta1 = myBean.selecionarContaCorrente(1);
        Assert.assertEquals(5, conta1.getSaldo().intValue());
        conta2 = myBean.selecionarContaCorrente(2);
        Assert.assertEquals(15, conta2.getSaldo().intValue());

        ContaCorrente contaFalsa = new ContaCorrente(null, 0);

        try {
            myBean.efetuarTransferencia(conta1, contaFalsa, 5);
            Assert.fail("Não gerou a exceção experada!");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MyException);
            Assert.assertEquals("CMT", ((MyException)e).getMethod());
            conta1 = myBean.selecionarContaCorrente(1);
            Assert.assertEquals(5, conta1.getSaldo().intValue());
        }
    }
}
