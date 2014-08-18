/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arquillian.example.arquilliantutorial;

import java.util.List;
import javax.ejb.Local;

@Local
public interface OrderRepository
{

    void addOrder(List<String> order);

    List<List<String>> getOrders();

    int getOrderCount();
}
