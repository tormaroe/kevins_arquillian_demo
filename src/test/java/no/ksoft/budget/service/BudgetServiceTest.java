package no.ksoft.budget.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import no.ksoft.budget.ds.Any;
import no.ksoft.budget.ds.Budget;
import no.ksoft.budget.ds.BudgetPost;
import no.ksoft.budget.ds.BudgetPostTag;
import no.ksoft.budget.service.impl.BudgetServiceImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author kevin
 */
@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
@Transactional(TransactionMode.ROLLBACK)
public class BudgetServiceTest {

    private static final Logger LOGGER = Logger.getLogger(BudgetServiceTest.class.getName());
    @Inject 
    private BudgetService budgetService;

    public BudgetServiceTest() {
    }

    @Deployment
    public static Archive<?> createTestableDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "service.war");
        war.addClass(Any.class);
        war.addClass(Budget.class);
        war.addClass(BudgetPost.class);
        war.addClass(BudgetPostTag.class);
        war.addClass(Service.class);
        war.addClass(BudgetService.class);
        war.addClass(BudgetServiceImpl.class);
        war.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        LOGGER.info(war.toString(Formatters.VERBOSE));
        return war;
    }

    @Test
    public void testFindAllBudgets() {
        List<Budget> all = budgetService.findAll();
        assertEquals(true, all.isEmpty());
        insertABudget();
        all = budgetService.findAll();
        assertEquals(1, all.size());
    }

    @Test
    public void testFindBudgetById() {
        Budget expected = insertABudget();
        assertTrue(expected.getId() > 0);

        List<Budget> all = budgetService.findAll();
        assertEquals(1, all.size());

        Budget result = budgetService.findById(expected.getId());
        assertEquals(expected, result);
    }

    @Test
    public void testMakePersistent() {
        Budget expected = insertABudget();
        Budget test = budgetService.findById(expected.getId());
        assertEquals(expected, test);
    }

    private Budget insertABudget() {
        final Budget b = new Budget();
        b.setEndDate(new Date(System.currentTimeMillis()));
        b.setStartDate(new Date(System.currentTimeMillis()));
        return budgetService.makePersistent(b);
    }
}
