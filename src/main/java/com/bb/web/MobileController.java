package com.bb.web;

import com.bb.domain.*;
import com.bb.domain.ref.RefPaymentType;
import com.bb.reference.*;
import com.bb.service.*;
import com.bb.util.AutowiredLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RequestMapping("/mobile")
@Controller
public class MobileController {

    @AutowiredLogger
    private Logger logger;

    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerProductController customerProductController;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AvatarService avatarService;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerProductService customerProductService;
    @Autowired
    private MobileService mobileService;
    @Autowired
    private LoginService loginService;


    @RequestMapping(value = "/register/create", method = RequestMethod.GET)
    public
    @ResponseBody
    Customer registerCreate(@RequestParam String username,
                            @RequestParam String email,
                            @RequestParam String password,
                            Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/register/create..............");
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPassword(password);

        customerController.create(customer, new DirectFieldBindingResult(customer, "customer"), uiModel, httpServletRequest);
        if (uiModel.containsAttribute("usernameUniqueError") || uiModel.containsAttribute("emailUniqueError")) {
            customer.setStatus(CustomerStatus.UsernameOrEmailExists);
        }
        return customer;
    }

    @RequestMapping(value = "/register/list", method = RequestMethod.GET)
    public
    @ResponseBody
    MobileRegisterList registerList(Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/register/list..............");
        MobileRegisterList list = mobileService.getRegisterList();
        return list;
    }

    @RequestMapping(value = "/register/submit", method = RequestMethod.GET)
    public
    @ResponseBody
    String registerSubmit(@RequestParam Long cid,
                          @RequestParam Long commitId,
                          @RequestParam Long stakeId,
                          @RequestParam(required = false) Long gymCardId,
                          @RequestParam(required = false) Long paymentTypeId,
                          @RequestParam(required = false) String accountNumber,
                          @RequestParam(required = false) String accountName,
                          Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/register/submit..............");
        String result = "success";

        try {
            Customer customer = Customer.findCustomer(cid);
            ProductCommit productCommit = ProductCommit.findProductCommit(commitId);
            ProductStake productStake = ProductStake.findProductStake(stakeId);
            Card card = Card.findCard(gymCardId);

            CustomerProduct customerProduct = new CustomerProduct();
            customerProduct.setCustomer(customer);
            customerProduct.setProductCommit(productCommit);
            customerProduct.setProductStake(productStake);
            customerProduct.setStartDate(new Date());
            customer.persist();

            if (gymCardId != null) {
                CustomerCard customerCard = new CustomerCard();
                customerCard.setCustomer(customer);
                customerCard.setCard(card);
                customerCard.setIssuedDate(new Date());
                customerCard.setStatus("未使用");
                customerCard.persist();
            }

            if (paymentTypeId != null) {
                RefPaymentType refPaymentType = RefPaymentType.findRefPaymentType(paymentTypeId);
                CustomerPayment customerPayment = new CustomerPayment();
                customerPayment.setCustomer(customer);
                if (accountNumber != null) {
                    customerPayment.setAccountId(accountNumber);
                }
                if (accountName != null) {
                    //TODO: save account name
                }
                customerPayment.setPaymentType(refPaymentType);
                customerPayment.setStartDate(new Date());
                customerPayment.persist();
            }
        } catch (Exception e) {
            logger.error("error", e);
            result = "failure";
        }

        return result;
    }

    @RequestMapping(value = "/register/login", method = RequestMethod.GET)
    public
    @ResponseBody
    Customer registerLogin(@RequestParam String username,
                           @RequestParam String password,
                           Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/register/login..............");
        Customer customer = Customer.findCustomersByLogin(username, password);
        return customer;
    }

    @RequestMapping(value = "/space/list", method = RequestMethod.GET)
    public
    @ResponseBody
    MobileSpaceList spaceList(@RequestParam Long cid, Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/space/list..............");
        MobileSpaceList mobileSpaceList = new MobileSpaceList();
        customerProductController.show(cid, uiModel);
        Map<String, Object> attributes = uiModel.asMap();
        mobileSpaceList.setOnVacation(attributes.get("isOnVacation"));
        //TODO: set avatar URL
        mobileSpaceList.setCurrentCustomerProduct(attributes.get("customerproduct"));
        mobileSpaceList.setFutureCustomerProduct(customerProductService.getFutureProduct(cid));

        mobileSpaceList.setWeekStatus(attributes.get("weekstatus"));
        return mobileSpaceList;
    }

    @RequestMapping(value = "/space/card", method = RequestMethod.GET)
    public
    @ResponseBody
    MobileSpaceCard spaceCard(@RequestParam Long cid, Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/space/card..............");
        MobileSpaceCard mobileSpaceCard = new MobileSpaceCard();
        customerProductController.card(cid, uiModel);
        Map<String, Object> attributes = uiModel.asMap();
        mobileSpaceCard.setCustomerCards(attributes.get("customercards"));
        mobileSpaceCard.setCardsToAdd(attributes.get("cards"));
        return mobileSpaceCard;
    }

    @RequestMapping(value = "/space/history", method = RequestMethod.GET)
    public
    @ResponseBody
    MobileSpaceHistory spaceHistory(@RequestParam Long cid, Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/space/history..............");
        MobileSpaceHistory mobileSpaceHistory = new MobileSpaceHistory();
        customerProductController.history(cid, 0L, uiModel);
        Map<String, Object> attributes = uiModel.asMap();
        mobileSpaceHistory.setCheckins(attributes.get("customercheckins"));
        return mobileSpaceHistory;
    }

    @RequestMapping(value = "/space/update/product", method = RequestMethod.GET)
    public
    @ResponseBody
    String spaceUpdateProduct(@RequestParam Long cid,
                              @RequestParam Long customerProductId,
                              @RequestParam Long commitNextWeek,
                              @RequestParam Long stakeNextWeek,
                              Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/space/update/product..............");

        try {
            CustomerProduct customerProduct = CustomerProduct.findCustomerProduct(customerProductId);
            Customer customer = Customer.findCustomer(cid);
            ProductCommit commit = ProductCommit.findProductCommit(commitNextWeek);
            ProductStake stake = ProductStake.findProductStake(stakeNextWeek);
            customerProduct.setCustomer(customer);
            customerProduct.setProductCommit(commit);
            customerProduct.setProductStake(stake);
            customerProductService.updateFutureProduct(customerProduct);
        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/space/update/password", method = RequestMethod.GET)
    public
    @ResponseBody
    String spaceUpdatePassword(@RequestParam Long cid,
                               @RequestParam String oldPassword,
                               @RequestParam String newPassword,
                               Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/space/update/password..............");

        try {
            loginService.changePassword(cid, oldPassword, newPassword);
        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/space/update/card", method = RequestMethod.GET)
    public
    @ResponseBody
    String spaceUpdateCard(@RequestParam Long cid,
                           @RequestParam Long cardId,
                           Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/space/update/card..............");

        try {
            Card card = Card.findCard(cardId);
            Customer customer = Customer.findCustomer(cid);
            CustomerCard customerCard = new CustomerCard();
            customerCard.setCard(card);
            customerCard.setCustomer(customer);
            customerCard.persist();
        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/checkin/start", method = RequestMethod.GET)
    public
    @ResponseBody
    String checkinStart(@RequestParam Long cid,
                        @RequestParam String gpsInfo,
                        Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/checkin/start..............");

        try {

        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/checkin/keepalive", method = RequestMethod.GET)
    public
    @ResponseBody
    String checkinKeepalive(@RequestParam Long cid,
                        @RequestParam String gpsInfo,
                        Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/checkin/keepalive..............");

        try {

        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/checkin/end", method = RequestMethod.GET)
    public
    @ResponseBody
    String checkinEnd(@RequestParam Long cid,
                        @RequestParam String gpsInfo,
                        Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/checkin/end..............");

        try {

        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/location/list", method = RequestMethod.GET)
    public
    @ResponseBody
    String locationList(@RequestParam Long cid,
                      @RequestParam String gpsInfo,
                      Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/location/list..............");

        try {

        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/location/add", method = RequestMethod.GET)
    public
    @ResponseBody
    String locationAdd(@RequestParam Long cid,
                        @RequestParam String gpsInfo,
                        Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/location/add..............");

        try {

        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/location/update", method = RequestMethod.GET)
    public
    @ResponseBody
    String locationUpdate(@RequestParam Long cid,
                        @RequestParam String gpsInfo,
                        Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println("/location/update..............");

        try {

        } catch (Exception e) {
            return "failure";
        }

        return "success";
    }

}
