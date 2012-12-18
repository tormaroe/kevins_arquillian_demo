package no.ksoft.budget.service;

import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import no.ksoft.budget.ds.Any;
import no.ksoft.budget.ds.Budget;
import no.ksoft.budget.ds.BudgetPost;
import no.ksoft.budget.ds.BudgetPostTag;
import no.ksoft.budget.service.impl.BudgetPostTagServiceImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
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
@Transactional(TransactionMode.ROLLBACK)
public class BudgetPostTagServiceTest {

    private static final Logger LOGGER = Logger.getLogger(BudgetPostTagServiceTest.class.getName());
    @Inject
    private BudgetPostTagService budgetPostTagService;

    @Deployment
    public static Archive<?> createTestableDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "service.war");
        war.addClass(Any.class);
        war.addClass(BudgetPostTag.class);
        war.addClass(BudgetPost.class);
        war.addClass(Budget.class);
        war.addClass(Service.class);
        war.addClass(BudgetPostTagService.class);
        war.addClass(BudgetPostTagServiceImpl.class);
        war.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));


        LOGGER.info(war.toString(Formatters.VERBOSE));
        return war;
    }

    public BudgetPostTagServiceTest() {
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
    public void testFindBudgetPostTagById() {
        BudgetPostTag expected = insertABudgetPostTag("testFindBudgetPostTagById");
        assertTrue(expected.getId() > 0);

        List<BudgetPostTag> all = budgetPostTagService.findAll();
        assertEquals(1, all.size());

        BudgetPostTag result = budgetPostTagService.findById(expected.getId());
        assertEquals(expected, result);
    }

    @Test
    public void testMakeBudgetPostTagPersistent() {
        BudgetPostTag bpt = insertABudgetPostTag("testMakeBudgetPostTagPersistent");
        assertTrue(bpt.getId() > 0);
    }

    private BudgetPostTag insertABudgetPostTag(String tagName) {
        BudgetPostTag bpt = new BudgetPostTag();
        bpt.setTagName(tagName);
        return budgetPostTagService.makePersistent(bpt);
    }
}
