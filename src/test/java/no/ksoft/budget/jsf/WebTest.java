package no.ksoft.budget.jsf;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import no.ksoft.budget.beans.IndexBean;
import no.ksoft.budget.beans.ViewBudgetBean;
import no.ksoft.budget.ds.Any;
import no.ksoft.budget.ds.Budget;
import no.ksoft.budget.ds.BudgetPost;
import no.ksoft.budget.ds.BudgetPostTag;
import no.ksoft.budget.service.BudgetPostService;
import no.ksoft.budget.service.BudgetPostTagService;
import no.ksoft.budget.service.BudgetService;
import no.ksoft.budget.service.Service;
import no.ksoft.budget.service.impl.BudgetPostServiceImpl;
import no.ksoft.budget.service.impl.BudgetPostTagServiceImpl;
import no.ksoft.budget.service.impl.BudgetServiceImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.jsfunit.api.InitialPage;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author kevin
 */
@RunWith(Arquillian.class)
@InitialPage("/index.xhtml")
public class WebTest {

    private static final Logger LOGGER = Logger.getLogger(WebTest.class.getName());
    @Inject
    private BudgetService budgetService;

    @Deployment(testable = true)
    public static Archive<?> createTestableDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(
                MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        final WebArchive war = ShrinkWrap.create(WebArchive.class, "budget.war");
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
        war.addClass(IndexBean.class);
        war.addClass(ViewBudgetBean.class);
        war.addAsWebResource(new File("src/main/webapp", "index.xhtml"));
        war.addAsWebResource(new File("src/main/webapp", "view-budget.xhtml"));
        war.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
        war.addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"), "faces-config.xml");
        war.addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-web.xml"), "jboss-web.xml");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        war.addAsLibraries(resolver.artifact("org.jboss.solder:solder-impl").resolveAsFiles());


        LOGGER.info(war.toString(Formatters.VERBOSE));
        return war;
    }

    @Before
    public void addSomeValues() {
        Budget b = new Budget();
        b.setEndDate(new Date(System.currentTimeMillis()));
        b.setStartDate(new Date(System.currentTimeMillis()));
        budgetService.makePersistent(b);
    }

    @Test
    @InitialPage("/index.xhtml")
    public void testIndexBean(JSFServerSession server, JSFClientSession client) throws IOException {
        //Are we looking at index.xhtml?
        assertEquals("/index.xhtml", server.getCurrentViewID());

        //There should be only one budget
        assertEquals(1, server.getManagedBeanValue("#{indexBean.allBudgets.size()}"));

        UIComponent totalAmountOfBudgetsOutput = server.findComponent("totaltAmountOfBudgets");

        //There should be only one budget listed on the page
        assertEquals("1", totalAmountOfBudgetsOutput.getAttributes().get("value").toString());

        //Click the view budget button
        client.click("viewBudgetForm:viewAbudget");

        //Have we now been transferred to the view-budget.xhtml page?
        assertTrue(server.getCurrentViewID().contains("view-budget"));
    }
}
