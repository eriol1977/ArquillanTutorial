/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arquillian.example.arquilliantutorial;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GreeterTest
{

    @Inject
    Greeter greeter;

    @Deployment
    public static JavaArchive createDeployment()
    {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Greeter.class, PhraseBuilder.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    @Test
    public void should_create_greeting()
    {
        Assert.assertEquals("Hello, Earthling!",
                            greeter.createGreeting("Earthling"));
        greeter.greet(System.out, "Earthling");
        Assert.assertEquals("Hello, Tony!",
                            greeter.createGreeting("Tony"));
        greeter.greet(System.out, "Tony");
    }
}
