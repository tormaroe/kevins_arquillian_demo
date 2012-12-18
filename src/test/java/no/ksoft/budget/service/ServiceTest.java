package no.ksoft.budget.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import no.ksoft.budget.ds.Any;
import no.ksoft.budget.ds.Budget;
import no.ksoft.budget.ds.BudgetPost;
import no.ksoft.budget.ds.BudgetPostTag;
import no.ksoft.budget.service.impl.BudgetPostServiceImpl;
import no.ksoft.budget.service.impl.BudgetPostTagServiceImpl;
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
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author kevin
 */
@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
@Transactional(TransactionMode.ROLLBACK)
public class ServiceTest {
    
    private static final Logger LOGGER = Logger.getLogger(ServiceTest.class.getName());
    @Inject
    private BudgetService budgetService;
    @Inject
    private BudgetPostTagService budgetPostTagService;
    @Inject
    private BudgetPostService budgetPostService;
    
    @Deployment
    public static Archive<?> createTestableDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "service.war");
        war.addClass(Any.class);
        war.addClass(Budget.class);
        war.addClass(BudgetPostTag.class);
        war.addClass(BudgetPost.class);
        war.addClass(Service.class);
        war.addClass(BudgetService.class);
        war.addClass(BudgetPostService.class);
        war.addClass(BudgetPostTagService.class);
        war.addClass(BudgetServiceImpl.class);
        war.addClass(BudgetPostServiceImpl.class);
        war.addClass(BudgetPostTagServiceImpl.class);
        war.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        
        
        LOGGER.info(war.toString(Formatters.VERBOSE));
        return war;
    }
    
    public ServiceTest() {
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
    public void testFindAllBudgetPosts() {
        
        List<BudgetPost> all = budgetPostService.findAll();
        assertEquals(true, all.isEmpty());
        insertAbudgetPost(insertABudget(), insertABudgetPostTag("testFindAllBudgetPosts"));
        all = budgetPostService.findAll();
        assertEquals(1, all.size());
    }
    
    @Test
    public void testFindAllBudgetPostTagss() {
        
        List<BudgetPostTag> all = budgetPostTagService.findAll();
        assertEquals(true, all.isEmpty());
        insertABudgetPostTag("testFindAllBudgetPostTagss");
        all = budgetPostTagService.findAll();
        assertEquals(1, all.size());
    }
    
    @Test
    public void testFindBudgetById() {
        final Budget expected = insertABudget();
        assertTrue(expected.getId() > 0);
        
        List<Budget> all = budgetService.findAll();
        assertEquals(1, all.size());
        
        Budget result = budgetService.findById(expected.getId());
        assertEquals(expected, result);
    }
    
    @Test
    public void testFindBudgetPostTagById() {
        BudgetPostTag expected = insertABudgetPostTag("testFindBudgetPostTagById");
        assertTrue(expected.getId() > 0);
        
        List<BudgetPostTag> all = budgetPostTagService.findAll();
        assertEquals(1, all.size());
        
        BudgetPostTag result = budgetPostTagService.findById(expected.getId());
        assertEquals(expected, result);
    }
    
    
    @Test
    public void testFindBudgetPostById() {
        BudgetPost expected = insertAbudgetPost(insertABudget(), insertABudgetPostTag("testFindBudgetPostById"));
        assertTrue(expected.getId() > 0);
        
        List<BudgetPost> all = budgetPostService.findAll();
        assertEquals(1, all.size());
        
        BudgetPost result = budgetPostService.findById(expected.getId());
        assertEquals(expected, result);
    }
    
    @Test
    public void testMakeBudgetPersistent() {
        assertNotNull(budgetService);
        final Budget b = insertABudget();
        assertTrue(b.getId() > 0);
    }
    
    @Test
    public void testMakeBudgetPostPersistent() {
        Budget b = insertABudget();
        BudgetPostTag bpt = insertABudgetPostTag("testMakeBudgetPostPersistent");
        BudgetPost bp = insertAbudgetPost(b, bpt);
        assertTrue(bp.getId() > 0);
    }
    
    @Test
    public void testMakeBudgetPostTagPersistent() {
        BudgetPostTag bpt = insertABudgetPostTag("testMakeBudgetPostTagPersistent");
        assertTrue(bpt.getId() > 0);
    }
    
    private BudgetPost insertAbudgetPost(Budget b, BudgetPostTag bpt) {
        final BudgetPost bp = new BudgetPost();
        bp.setBudget(b);
        bp.setAmount(100d);
        bp.setAmountType(0);
        bp.setBudgetPostTag(bpt);
        bp.setDescription("A budget post");
        return budgetPostService.makePersistent(bp);
    }
    
    private Budget insertABudget() {
        final Budget b = new Budget();
        b.setEndDate(new Date(System.currentTimeMillis()));
        b.setStartDate(new Date(System.currentTimeMillis()));
        return budgetService.makePersistent(b);
    }

    private BudgetPostTag insertABudgetPostTag(String tagName) {
        BudgetPostTag bpt = new BudgetPostTag();
        bpt.setTagName(tagName);
        return budgetPostTagService.makePersistent(bpt);
    }
}
