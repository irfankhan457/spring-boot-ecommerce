package com.luv2code.springbootecommerce.config;

import com.luv2code.springbootecommerce.entity.Country;
import com.luv2code.springbootecommerce.entity.Product;
import com.luv2code.springbootecommerce.entity.ProductCategory;
import com.luv2code.springbootecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
        // disable HTTP method for Product: PUT, POST and DELETE
        DisabledHttpMethod(Product.class, config, theUnsupportedActions);
        // disable HTTP method for ProductCategory: PUT, POST and DELETE
        DisabledHttpMethod(ProductCategory.class, config, theUnsupportedActions);
        // disable HTTP method for Country : PUT, POST and DELETE
        DisabledHttpMethod(Country.class, config, theUnsupportedActions);
        // disable HTTP method for State : PUT, POST and DELETE
        DisabledHttpMethod(State.class, config, theUnsupportedActions);

        // Call an internal Helper Method
        exposeID(config);
    }

    private void DisabledHttpMethod(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeID(RepositoryRestConfiguration config) {
        // expose entity ids


        // get a ,list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // create and array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // get the enetity type for the entities
        for(EntityType tempEntityType: entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // expose the enetity Ids for the Array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
