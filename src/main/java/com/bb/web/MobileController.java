package com.bb.web;

import com.bb.domain.*;
import com.bb.domain.ref.RefPaymentType;
import com.bb.reference.*;
import com.bb.service.*;
import com.bb.util.AutowiredLogger;
import com.bb.util.GpsDistanceCalc;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        logger.info("/register/create..............");
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
        logger.info("/register/list..............");
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
        logger.info("/register/submit..............");
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
            customerProduct.persist();

            if (gymCardId != null) {
                CustomerCard customerCard = new CustomerCard();
                customerCard.setCustomer(customer);
                customerCard.setCard(card);
                customerCard.setIssuedDate(new Date());
                customerCard.setStatus("NOT USED");
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public
    @ResponseBody
    Customer login(@RequestParam String usernameOrEmail,
                           @RequestParam String password,
                           Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("/login..............");
        Customer customer = null;
        try {
            customer = Customer.findCustomersByUsernameOrEmail(usernameOrEmail, password);
        } catch (Exception e) {
            e.printStackTrace();
            customer = new Customer();
        }
        return customer;
    }

    @RequestMapping(value = "/space/list", method = RequestMethod.GET)
    public
    @ResponseBody
    MobileSpaceList spaceList(@RequestParam Long cid, Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("/space/list..............");
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
        logger.info("/space/card..............");
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
        logger.info("/space/history..............");
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
        logger.info("/space/update/product..............");

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
            logger.error("{}", e);
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
        logger.info("/space/update/password..............");

        try {
            loginService.changePassword(cid, oldPassword, newPassword);
        } catch (Exception e) {
            logger.error("{}", e);
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
        logger.info("/space/update/card..............");

        try {
            Card card = Card.findCard(cardId);
            Customer customer = Customer.findCustomer(cid);
            CustomerCard customerCard = new CustomerCard();
            customerCard.setCard(card);
            customerCard.setCustomer(customer);
            customerCard.persist();
        } catch (Exception e) {
            logger.error("{}", e);
            return "failure";
        }

        return "success";
    }

    @RequestMapping(value = "/checkin/start", method = RequestMethod.GET)
    public
    @ResponseBody
    CustomerCheckin checkinStart(@RequestParam Long cid,
                        @RequestParam Float lat, @RequestParam Float lon,
                        Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("/checkinStart/start..............");
        CustomerCheckin customerCheckin = new CustomerCheckin();
        try {
            customerCheckin = mobileService.checkinStart(cid, lat, lon);
        } catch (Exception e) {
            logger.error("{}", e);
        }

        return customerCheckin;
    }

    @RequestMapping(value = "/checkin/keepalive", method = RequestMethod.GET)
    public
    @ResponseBody
    CustomerCheckin  checkinKeepalive(@RequestParam Long cid,
                            @RequestParam Double lat, @RequestParam Double lon,
                            @RequestParam Long customerCheckinId,
                            Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("/checkinStart/keepalive..............");
        CustomerCheckin customerCheckin = new CustomerCheckin();

        try {
            //TODO
            customerCheckin = mobileService.checkinKeepalive(cid, lat, lon, customerCheckinId);
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return customerCheckin;
    }

    @RequestMapping(value = "/checkin/end", method = RequestMethod.GET)
    public
    @ResponseBody
    CustomerCheckin checkinEnd(@RequestParam Long cid,
                      @RequestParam Double lat, @RequestParam Double lon,
                      @RequestParam Long customerCheckinId,
                      Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("/checkinStart/end..............");
        CustomerCheckin customerCheckin = new CustomerCheckin();

        try {
            //TODO
            customerCheckin = mobileService.checkinEnd(cid, lat, lon, customerCheckinId);
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return customerCheckin;
    }

    @RequestMapping(value = "/location/list", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MobileLocationListItem> locationList(@RequestParam Long cid,
                        @RequestParam Float lat, @RequestParam Float lon,
                        @RequestParam Long radiusInMeter,
                        Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("/location/list..............");
        List<MobileLocationListItem> list = new ArrayList<MobileLocationListItem>();
        try {
            if (radiusInMeter <= 20000) {
                GpsDistanceCalc.GpsRange range = GpsDistanceCalc.getGpsRange(lat, lon, radiusInMeter);
                logger.info("range:{}", range);
                List<Location> locations = Location.findAllLocationsInRange(range);
                logger.info("locations:{}", locations);
                for (Location location : locations) {
                    MobileLocationListItem item = new MobileLocationListItem();
                    item.setLatitude(location.getLatitude());
                    item.setLongitude(location.getLongitude());
                    item.setLocationId(location.getId());
                    item.setName(location.getName());
                    item.setAddress(location.getAddress());
                    logger.info("item:{}", item);
                    list.add(item);
                }
                logger.info("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error:", e);
        }

        return list;
    }

    @RequestMapping(value = "/location/add", method = RequestMethod.GET)
    public
    @ResponseBody
    Location locationAdd(@RequestParam Long cid,
                       @RequestParam Double lat, @RequestParam Double lon,
                       @RequestParam String name, @RequestParam String address,
                       Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("/location/add..............");
        Location location = new Location();

        try {
            location.setCustomer(Customer.findCustomer(cid));
            location.setLatitude(lat);
            location.setLongitude(lon);
            location.setName(name);
            location.setAddress(address);
            location.setStatus(LocationStatus.Todo);
            location.persist();
        } catch (Exception e) {
            logger.error("error:", e);
        }

        return location;
    }


}
