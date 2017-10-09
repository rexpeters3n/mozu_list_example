package com.h3dg3wytch;

import com.mozu.api.MozuApiContext;
import com.mozu.api.clients.commerce.catalog.admin.CategoryClient;
import com.mozu.api.contracts.appdev.AppAuthInfo;
import com.mozu.api.resources.commerce.customer.CustomerAccountResource;
import com.mozu.api.security.AppAuthenticator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class CustomerList {

    private static final String CONFIG_PROPERTIES = "config.properties";

    private static Properties properties;
    private static CustomerAccountResource customerAccountResource;

    public static void run(){
        setUp();
        authenicateAppplicationToApi();
        createCustomerAccountResource();
        getTotalNumberOfCustomers();
    }

    private static void createCustomerAccountResource() {
        customerAccountResource = new CustomerAccountResource(createMoziApiContext());
    }


    public static void setUp(){
        properties = new Properties();
        loadConfigurationProperties();
    }

    private static void loadConfigurationProperties() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream(CONFIG_PROPERTIES)){
            properties.load(resourceStream);
        }catch (FileNotFoundException e){
            System.out.println("ERROR: Config file not found! Please include in project!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void getTotalNumberOfCustomers() {
        try{
            System.out.println("Number of Customer Accounts: " + customerAccountResource.getAccounts().getTotalCount());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static MozuApiContext createMoziApiContext() {
        int tenantId =  Integer.parseInt(properties.getProperty("tenant_id"));
        int siteId= Integer.parseInt(properties.getProperty("site_id"));
        return new MozuApiContext(tenantId, siteId);

    }

    public static void authenicateAppplicationToApi(){
        AppAuthInfo appAuthInfo = new AppAuthInfo();
        //Setup the key
        appAuthInfo.setApplicationId(properties.getProperty("application_id"));
        appAuthInfo.setSharedSecret(properties.getProperty("secret_key"));
        AppAuthenticator.initialize(appAuthInfo);

    }

    public static Properties getProperties() {
        return properties;
    }
}
