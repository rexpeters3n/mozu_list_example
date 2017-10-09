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

    private static Properties properties;

    public static void main(String[] args) {

        setUp();

        AppAuthInfo appAuthInfo = new AppAuthInfo();

        //Setup the key
        appAuthInfo.setApplicationId("rex_petersen");
        appAuthInfo.setSharedSecret(properties.getProperty("secret_key"));

        AppAuthenticator.initialize(appAuthInfo);

        int tenantId =  Integer.parseInt(properties.getProperty("tenant_id"));
        int siteId= Integer.parseInt(properties.getProperty("site_id"));

        MozuApiContext apiContext = new MozuApiContext(tenantId, siteId);

        CustomerAccountResource resource = new CustomerAccountResource(apiContext);

        try{
            System.out.println("Number of Customer Accounts: " + resource.getAccounts().getTotalCount());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setUp(){
        properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream("config.properties")){
            properties.load(resourceStream);
        }catch (FileNotFoundException e){
            System.out.println("ERROR: Config file not found! Please include in project!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }
}
