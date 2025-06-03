package com.mdempire.data.repositories;

import com.mdempire.data.models.Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class SecuritiesTest {

    @Autowired
    private Securities securitiesRepository;

    @BeforeEach
    public void setUp() {
        securitiesRepository.deleteAll();
    }

    @Test
    public void registerSecurity_countIsOne_test() {
        Security securityId = registerSecurity();
        assertNotNull(securityId);
        assertEquals(1, securitiesRepository.count());
    }

    @Test
    public void registerSecurity_findSecurityById(){
        var saveSecurity = securitiesRepository.save(registerSecurity());
        assertEquals(1, securitiesRepository.count());
        Security security = securitiesRepository.findById(saveSecurity.getId()).get();
        assertNotNull(security);
    }

    @Test
    public void registerSecurity_deleteSecurityById(){
        var savedSecurity = securitiesRepository.save(registerSecurity());
        assertEquals(1, securitiesRepository.count());
        securitiesRepository.deleteById(savedSecurity.getId());
        assertEquals(0, securitiesRepository.count());
    }


    private Security registerSecurity() {
        Security security = new Security();
        security.setFullName("Security Man");
        security.setPhoneNumber("0909090909");
        security.setHomeAddress("Los Angeles");

        return securitiesRepository.save(security);
    }


}