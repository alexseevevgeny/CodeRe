package datango.ui.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import datango.api.helpers.EntitiesPresentCheck;
import datango.api.helpers.SearchEntityOu;
import datango.api.requests.*;
import datango.api.utils.GetterCollector;
import datango.ui.pages.collaborator.Collaborator;
import datango.ui.pages.collaborator.administration.UserPage;
import datango.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static datango.api.data.consts.IndentitiesConsts.*;
import static datango.config.AppConfigProvider.get;
import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.enums.FormFields.*;
import static datango.utils.RandomUtils.getRandomString;

/**
 * @author Sergey Kuzhel
 */

@Slf4j
public class TestDataProvider {
    public static final String BASE_OU = get().defOuName();
    public static final String ROOT_OU = get().defRootOuName();
    public static final String SERVICE_OU = get().defServiceOuName();
    public static final String SERVICE_USER = get().defLogin();
    public static final String INACTIVE_USER = get().inactiveLogin();
    public static final String GUEST_USER= get().defGuestUser();
    public static final String SERVICE_ROLE = get().defGroupName();
    private static final String GUEST_NAME = "guest (Guest)";
    private static final String SERVICE_OU_REG_CODE = "082331";
    private static final String INACTIVE_NAME = "inactive (inactive inactive)";
    private static final String SERVICE_NAME = "adtadmin (adtadmin adtadmin)";

    private Collaborator collaborator;
    private UserPage orgViewPage;
    private ObjectMapper om;
    private Ou ou;
    private Group group;
    private User user;
    private Permission permission;
    private GetterCollector get = new GetterCollector();
    private SearchEntityOu ouSearch = new SearchEntityOu();
    private EntitiesPresentCheck ouCheck = new EntitiesPresentCheck();

    public TestDataProvider(UserPage orgViewPage) {
        this.orgViewPage = orgViewPage;
    }

    public TestDataProvider() {
        om = new ObjectMapper();
        ou = new Ou();
        group = new Group();
        user = new User();
        permission = new Permission();
    }

    public void createOUData() {
        boolean isOuPresent = isEntityPresent(orgViewPage.getOuNames(), BASE_OU);
        if (!isOuPresent) {
            orgViewPage.clickOU(ROOT_OU);
            orgViewPage.addOU(BASE_OU, TEST_EMPTY);
            orgViewPage.clickOU(BASE_OU);
            orgViewPage.inputEntityEditRegCode(TEST_REG_CODE);
            orgViewPage.clickOU(BASE_OU);
            orgViewPage.clickOuSaveButton();
        }
        orgViewPage.clickOU(SERVICE_OU);
        isOuPresent = isEntityPresent(orgViewPage.getOuNames(), SERVICE_OU);
        if (!isOuPresent) {
            orgViewPage.clickOU(ROOT_OU);
            orgViewPage.addOU(SERVICE_OU, createId());
        }
    }

    public void createUserData() {
        orgViewPage.clickOU(SERVICE_OU);
        boolean isEntityPresent = isEntityPresent(orgViewPage.getEntityNames(), SERVICE_USER);
        orgViewPage.clickOU(SERVICE_OU);
        if (!isEntityPresent) {
            orgViewPage.addUser(getServiceUserInfo(SERVICE_USER));        }
        orgViewPage.clickOU(SERVICE_OU);
        isEntityPresent = isEntityPresent(orgViewPage.getEntityNames(), INACTIVE_USER);
        if (!isEntityPresent) {
            orgViewPage.addUser(getServiceUserInfo(INACTIVE_USER));
            orgViewPage.activateDeactivateEntity(INACTIVE_USER);
        }
        orgViewPage.clickOU(ROOT_OU);
        if (!orgViewPage.isEntityAcivated(GUEST_USER)) {
            orgViewPage.activateDeactivateEntity(GUEST_USER);
        }

    }

    public void createRoleData() {
        //stub
    }

    private boolean isEntityPresent(List<String> names, String name) {
        return !names.stream()
                .filter(e -> e.startsWith(name))
                .collect(Collectors.toList()).isEmpty();
    }

    public String createName() {
        return RandomUtils.getRandomString(7, ENGLISH_ALPHABET, true);
    }

    public String createId() {
        return RandomUtils.getRandomString(6, NUMERIC, false);
    }

    public Map<String, String> getRandomUserInfo() {
        HashMap<String, String> info = new HashMap<>();
        String name = getRandomString(7, ENGLISH_ALPHABET, true).toLowerCase();
        info.put(NAME.getValue(), name);
        info.put(LOGIN.getValue(), name);
        info.put(LAST_NAME.getValue(), name + TEST_LAST_NAME);
        info.put(EMAIL.getValue(), TEST_EMAIL);
        info.put(PASSWORD.getValue(), name);
        return info;
    }

    private Map<String, String> getServiceUserInfo(String name) {
        Map<String, String> info = new HashMap<>();
        info.put(NAME.getValue(), name);
        info.put(LOGIN.getValue(), name);
        info.put(LAST_NAME.getValue(), name);
        info.put(EMAIL.getValue(), TEST_EMPTY);
        info.put(PASSWORD.getValue(), name);
        return info;
    }

    //API test data----------------------------------------------------------------------------------
    public void createOU() {
        boolean isOuPresent = ouCheck.isOuPresent(BASE_OU, TYPE_OU);
        if (!isOuPresent) {
            log.info("BASE OU is created: ", ou.createOu(BASE_OU, TEST_REG_CODE, get.getOuId(ou.getOuRoot())).getBody().prettyPrint());
        }
        isOuPresent = ouCheck.isOuPresent(SERVICE_OU, TYPE_OU);
        if (!isOuPresent) {
            log.info("SERVICE OU is created: ", ou.createOu(SERVICE_OU, SERVICE_OU_REG_CODE, get.getOuId(ou.getOuRoot())).getBody().prettyPrint());
        }
    }

    public void createUsers() {
        String childOu = ouSearch.getTestOu(SERVICE_OU, TYPE_OU);
        boolean isUserPresent = ouCheck.isServiceUserPresent(SERVICE_NAME, TYPE_USER);
        if (!isUserPresent) {
            log.info("SERVICE USER is created: ", user.createUser(getServiceUserBody(SERVICE_USER, childOu)).getBody().prettyPrint());
        }
        isUserPresent = ouCheck.isServiceUserPresent(INACTIVE_NAME, TYPE_USER);
        if (!isUserPresent) {
            log.info("INACTIVE USER is created: ", user.createUser(getServiceUserBody(INACTIVE_USER, childOu)).getBody().prettyPrint());
            String inactiveOuId = ouSearch.getTestUser(INACTIVE_NAME, TYPE_USER).getOUUid();
            log.info("INACTIVE USER is deactivated: ", user.deactivationUser(inactiveOuId).getBody().prettyPrint());
        }
        String guestUserId = ouSearch.getGuestUser(GUEST_NAME, TYPE_USER).getOUUid();
        if (!ouSearch.getGuestUser(GUEST_NAME, TYPE_USER).getOUIsActive()) {
            log.info("GUEST USER is activated: ", user.activationUser(guestUserId).getBody().prettyPrint());
        }
    }

    public void createRole() {
        String ouId = ouSearch.getTestOu(SERVICE_OU, TYPE_OU);
        boolean isGroupPresent = ouCheck.isServiceUserPresent(SERVICE_ROLE, TYPE_GROUP);
        if (!isGroupPresent) {
            log.info("SERVICE ROLE is created: ", group.createGroup(SERVICE_ROLE, TEST_REG_CODE, ouId).getBody().prettyPrint());
        }
    }

    public void addPermissionToUser() {
        String userId = ouSearch.getTestUser(SERVICE_NAME, TYPE_USER).getOUUid();
        log.info("PERMISSIONS is added: ", permission.addAllPermissions(userId).getBody().prettyPrint());
    }

    public void createResponsibleUserForOU() {
        String serviceOuId = ouSearch.getTestOu(SERVICE_OU, TYPE_OU);
        String userId = ouSearch.getTestUser(SERVICE_NAME, TYPE_USER).getOUUid();
        log.info("RESPONSIBLE USER is selected: ", ou.createResponsibleUser(serviceOuId, SERVICE_OU, SERVICE_OU_REG_CODE, userId).getBody().prettyPrint());
    }

    private JsonNode getServiceUserBody(String name, String ouId) {
        ObjectNode body = om.createObjectNode();
        body.put("login", name);
        body.put("firstName", name);
        body.put("lastName", name);
        body.put("email", TEST_EMPTY);
        body.put("password", name);
        body.put("ouId", ouId);
        return body;
    }
}
