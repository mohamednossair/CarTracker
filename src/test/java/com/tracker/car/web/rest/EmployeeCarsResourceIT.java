package com.tracker.car.web.rest;

import com.tracker.car.CarTrackerApp;
import com.tracker.car.domain.EmployeeCars;
import com.tracker.car.domain.Employee;
import com.tracker.car.domain.Car;
import com.tracker.car.repository.EmployeeCarsRepository;
import com.tracker.car.service.EmployeeCarsService;
import com.tracker.car.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.tracker.car.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EmployeeCarsResource} REST controller.
 */
@SpringBootTest(classes = CarTrackerApp.class)
public class EmployeeCarsResourceIT {

    private static final Double DEFAULT_PREVIOUS_READING = 0D;
    private static final Double UPDATED_PREVIOUS_READING = 1D;

    private static final Double DEFAULT_CURRENT_READING = 0D;
    private static final Double UPDATED_CURRENT_READING = 1D;

    private static final Integer DEFAULT_WORKING_DAYS = 1;
    private static final Integer UPDATED_WORKING_DAYS = 2;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmployeeCarsRepository employeeCarsRepository;

    @Autowired
    private EmployeeCarsService employeeCarsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEmployeeCarsMockMvc;

    private EmployeeCars employeeCars;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeCarsResource employeeCarsResource = new EmployeeCarsResource(employeeCarsService);
        this.restEmployeeCarsMockMvc = MockMvcBuilders.standaloneSetup(employeeCarsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCars createEntity(EntityManager em) {
        EmployeeCars employeeCars = new EmployeeCars()
            .previousReading(DEFAULT_PREVIOUS_READING)
            .currentReading(DEFAULT_CURRENT_READING)
            .workingDays(DEFAULT_WORKING_DAYS)
            .updateDate(DEFAULT_UPDATE_DATE);
        // Add required entity
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeCars.setEmployee(employee);
        // Add required entity
        Car car = CarResourceIT.createEntity(em);
        em.persist(car);
        em.flush();
        employeeCars.setCar(car);
        return employeeCars;
    }

    @BeforeEach
    public void initTest() {
        employeeCars = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeCars() throws Exception {
        int databaseSizeBeforeCreate = employeeCarsRepository.findAll().size();

        // Create the EmployeeCars
        restEmployeeCarsMockMvc.perform(post("/api/employee-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeCars)))
            .andExpect(status().isCreated());

        // Validate the EmployeeCars in the database
        List<EmployeeCars> employeeCarsList = employeeCarsRepository.findAll();
        assertThat(employeeCarsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeCars testEmployeeCars = employeeCarsList.get(employeeCarsList.size() - 1);
        assertThat(testEmployeeCars.getPreviousReading()).isEqualTo(DEFAULT_PREVIOUS_READING);
        assertThat(testEmployeeCars.getCurrentReading()).isEqualTo(DEFAULT_CURRENT_READING);
        assertThat(testEmployeeCars.getWorkingDays()).isEqualTo(DEFAULT_WORKING_DAYS);
        assertThat(testEmployeeCars.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createEmployeeCarsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeCarsRepository.findAll().size();

        // Create the EmployeeCars with an existing ID
        employeeCars.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeCarsMockMvc.perform(post("/api/employee-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeCars)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCars in the database
        List<EmployeeCars> employeeCarsList = employeeCarsRepository.findAll();
        assertThat(employeeCarsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeCars() throws Exception {
        // Initialize the database
        employeeCarsRepository.saveAndFlush(employeeCars);

        // Get all the employeeCarsList
        restEmployeeCarsMockMvc.perform(get("/api/employee-cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeCars.getId().intValue())))
            .andExpect(jsonPath("$.[*].previousReading").value(hasItem(DEFAULT_PREVIOUS_READING.doubleValue())))
            .andExpect(jsonPath("$.[*].currentReading").value(hasItem(DEFAULT_CURRENT_READING.doubleValue())))
            .andExpect(jsonPath("$.[*].workingDays").value(hasItem(DEFAULT_WORKING_DAYS)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeCars() throws Exception {
        // Initialize the database
        employeeCarsRepository.saveAndFlush(employeeCars);

        // Get the employeeCars
        restEmployeeCarsMockMvc.perform(get("/api/employee-cars/{id}", employeeCars.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeCars.getId().intValue()))
            .andExpect(jsonPath("$.previousReading").value(DEFAULT_PREVIOUS_READING.doubleValue()))
            .andExpect(jsonPath("$.currentReading").value(DEFAULT_CURRENT_READING.doubleValue()))
            .andExpect(jsonPath("$.workingDays").value(DEFAULT_WORKING_DAYS))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeCars() throws Exception {
        // Get the employeeCars
        restEmployeeCarsMockMvc.perform(get("/api/employee-cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeCars() throws Exception {
        // Initialize the database
        employeeCarsService.save(employeeCars);

        int databaseSizeBeforeUpdate = employeeCarsRepository.findAll().size();

        // Update the employeeCars
        EmployeeCars updatedEmployeeCars = employeeCarsRepository.findById(employeeCars.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeCars are not directly saved in db
        em.detach(updatedEmployeeCars);
        updatedEmployeeCars
            .previousReading(UPDATED_PREVIOUS_READING)
            .currentReading(UPDATED_CURRENT_READING)
            .workingDays(UPDATED_WORKING_DAYS)
            .updateDate(UPDATED_UPDATE_DATE);

        restEmployeeCarsMockMvc.perform(put("/api/employee-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeCars)))
            .andExpect(status().isOk());

        // Validate the EmployeeCars in the database
        List<EmployeeCars> employeeCarsList = employeeCarsRepository.findAll();
        assertThat(employeeCarsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCars testEmployeeCars = employeeCarsList.get(employeeCarsList.size() - 1);
        assertThat(testEmployeeCars.getPreviousReading()).isEqualTo(UPDATED_PREVIOUS_READING);
        assertThat(testEmployeeCars.getCurrentReading()).isEqualTo(UPDATED_CURRENT_READING);
        assertThat(testEmployeeCars.getWorkingDays()).isEqualTo(UPDATED_WORKING_DAYS);
        assertThat(testEmployeeCars.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeCars() throws Exception {
        int databaseSizeBeforeUpdate = employeeCarsRepository.findAll().size();

        // Create the EmployeeCars

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCarsMockMvc.perform(put("/api/employee-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeCars)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCars in the database
        List<EmployeeCars> employeeCarsList = employeeCarsRepository.findAll();
        assertThat(employeeCarsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeCars() throws Exception {
        // Initialize the database
        employeeCarsService.save(employeeCars);

        int databaseSizeBeforeDelete = employeeCarsRepository.findAll().size();

        // Delete the employeeCars
        restEmployeeCarsMockMvc.perform(delete("/api/employee-cars/{id}", employeeCars.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EmployeeCars> employeeCarsList = employeeCarsRepository.findAll();
        assertThat(employeeCarsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeCars.class);
        EmployeeCars employeeCars1 = new EmployeeCars();
        employeeCars1.setId(1L);
        EmployeeCars employeeCars2 = new EmployeeCars();
        employeeCars2.setId(employeeCars1.getId());
        assertThat(employeeCars1).isEqualTo(employeeCars2);
        employeeCars2.setId(2L);
        assertThat(employeeCars1).isNotEqualTo(employeeCars2);
        employeeCars1.setId(null);
        assertThat(employeeCars1).isNotEqualTo(employeeCars2);
    }
}
