package com.mdempire.data.repositories;

import com.mdempire.data.models.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class ResidentsTest {

        @Autowired
        private Residents residents;
        @BeforeEach
        void setUp() {
            residents.deleteAll();
        }

        @Test
        public void registerResidentTest(){
            registerResident();
            var savedResident = residents.save(registerResident());
            assertEquals(1, residents.count());
            assertNotNull(savedResident.getId());
        }

        @Test
        public void registerResident_findResidentById(){
            registerResident();
            var savedResident = residents.save(registerResident());
            assertEquals(1, residents.count());
            Resident resident = residents.findById(savedResident.getId()).get();
            assertNotNull(resident);
        }

        @Test
        public void registerResident_deleteResidentById(){
            registerResident();
            var savedResident = residents.save(registerResident());
            assertEquals(1, residents.count());
            residents.deleteById(savedResident.getId());
            assertEquals(0, residents.count());
        }

        @Test
        public void savedTwoResidents_updateResidentWithId_countIsOneTest(){
            Resident firstResidents = new Resident();
            Resident firstResidentId = residents.save(firstResidents);
            assertEquals(1, residents.count());

            Resident secondResidents = new Resident();
            secondResidents.setId(firstResidents.getId());
            secondResidents.setFullName("Moses Idowu");
            Resident secondResidentId = residents.save(secondResidents);

            assertEquals(1, residents.count());
            assertNotNull(secondResidentId.getId());
            assertEquals(firstResidentId.getId(), secondResidentId.getId());
            assertEquals("Moses Idowu", residents.findById(firstResidents.getId()).get().getFullName());

        }

        private Resident registerResident(){
            Resident resident = new Resident();
            resident.setFullName("Yusuf Usman");
            resident.setPhoneNumber("1234567890");
            resident.setHomeAddress("Lagos Island");
            return resident;
        }
}