package com.example.ichatclient.model;

import com.example.ichatclient.util.FactoryConfiguration;
import com.example.ichatclient.to.ClientTo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClientModel {

    //save client
    public boolean save(ClientTo client) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(client);
            transaction.commit();
            return true;
        }catch(Exception e){
            System.out.println(e);
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }


    //update client
    public boolean update(ClientTo client) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.update(client);
            transaction.commit();
            return true;
        }catch(Exception e){
            System.out.println(e);
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }


    //delete client
    public boolean delete(ClientTo client) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.delete(client);
            transaction.commit();
            return true;
        }catch(Exception e){
            System.out.println(e);
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    //search client
    public ClientTo search(String username) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            ClientTo client = session.get(ClientTo.class, username);
            transaction.commit();
            return client;
        }catch(Exception e){
            System.out.println(e);
            transaction.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public List<ClientTo> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{

            String hql = "FROM client";
            Query query = session.createQuery(hql);
            List<ClientTo> list= query.list();
            return list;

        }catch(Exception e){
            transaction.rollback();
            return null;
        }finally {
            session.close();
        }
    }
}
