package com.nt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nt.model.Admins;
import com.nt.model.Cart;
import com.nt.model.Category;
import com.nt.model.Message;
import com.nt.model.Order;
import com.nt.model.Products;
import com.nt.model.Users;
import com.nt.model.WishList;
import com.nt.service.IAdminsService;
import com.nt.service.ICartService;
import com.nt.service.ICategoryService;
import com.nt.service.IMailOperation;
import com.nt.service.IOrderService;
import com.nt.service.IProductsService;
import com.nt.service.IUsersService;
import com.nt.service.IWishlistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private IUsersService userService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IAdminsService adminService;
	@Autowired
	private IProductsService productService;
	@Autowired
	private ICartService cartService;
	@Autowired
	private IWishlistService wishlistService;
	@Autowired
	private IMailOperation mailOperation;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	HttpSession session;

	
	// It returns Home Page
	@GetMapping("/")
	public String homePage(Map<String, Object> map) {
		
		// get latest 3 products and save to map
		List<Products> productList = productService.getLatestThreeProducts();
		map.put("productList", productList);

		// get top deals 4 products and save to map
		List<Products> topDealsProducts = productService.getTopDealsFourProducts();
		map.put("topDealsProdcuts", topDealsProducts);

		//session.setAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}

	
	// It returns User registration page
	@GetMapping("/register")
	public String showRegisterForm() {
		return "register";
	}

	
	// It performs pre-operation before registring the user 
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") Users user, @RequestParam("password") String password) {
        //send verification code to user for email verification
		boolean flag=mailOperation.sendVerficationCode(user.getEmail());
       
		if(flag) {  //If email is successfully sent
			//Display message
			Message  message = new Message("We'ev sent a verification code to "+user.getEmail(),  "success", "alert-success"); 
			session.setAttribute("message", message);
			//Store the user registration details in session scope for later use
			session.setAttribute("userDetails", user);
			//first encrypt password 
			String pwd=encoder.encode(password);
			//Store the password in the session scope for later use
			session.setAttribute("password", pwd);
			return  "redirect:enterOTP";
		}else { //If email is not sent successfully
			Message  message = new Message("Unable sent a verification code to "+user.getEmail(),  "error", "alert-danger"); 
			session.setAttribute("message", message);
			return  "redirect:register";
		}
	}
    
	
	//If performs user registration operation
	@GetMapping("/registerUser")
	public String saveUserDetails() {
        //Get user registration details from session scope
		Users user=(Users)session.getAttribute("userDetails");
		//Remove user registration details from session scope
		session.removeAttribute("userDetails");
		//Get user entered (encrypted) password from session scope
		String password=(String) session.getAttribute("password");
		//Remove password from session scope
		session.removeAttribute("password");
		
		// call registerUser(com.nt.service.UsersServiceImpl.java) method to save
		String  result= userService.registerUser(user, password); //returns successful message
		
        //Display message
		Message  message = new Message(result,  "success", "alert-success"); 
		session.setAttribute("message", message);
		
		return "redirect:login";
	}


	// It returns user login page
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	
	// It returns Admin login page
	@GetMapping("/adminlogin")
	public String adminLogin() {
		return "adminlogin";
	}


	// It returns Admin Dashboard (Admin Home page)
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('admin')") //specifying authorization
	public String showAdminPage(Map<String, Object> map) {
		// Store the list of categories into the map object
		// This helps the admins to select a category when adding a new product
		List<Category> cat = categoryService.getAllCategory();
		map.put("category", cat);
		return "admin";
	}
	

	// It return page for resetting password
	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgot_password";
	}

	
	// It performs user verification operation before resetting the password
	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam("email") String email) {
		//Check is given email is available or not 
		boolean flag = userService.isEmailAvailable(email);
      
		if (!flag) {//user is not found 
			//Display message
			Message message = new Message("Email not found! Try with another email!", "error", "alert-danger");
			session.setAttribute("message", message);
			//redirect to same page
			return "redirect:forgotPassword";
		}else{ //user is found
		//Send verification OTP to user through email
		boolean flag2 = mailOperation.sendVerficationCode(email);
		
		if(flag2) {// Email sent successfully
			Message message = new Message("We'ev sent a password reset code to " + email, "success", "alert-success");
			session.setAttribute("message", message);
			//Redirect to OTP page
			return "redirect:enterOTP";
		}else {// Email is unable to sent
			Message message = new Message("Unable to  sent a password reset code to " + email, "error", "alert-danger");
			session.setAttribute("message", message);
			//Redirect to same page
			return "redirect:forgotPassword";
		}
		}
	}

	
	//It returns page where use can enter OTP
	@GetMapping("enterOTP")
	public String showOTPPage() {
		return "otp_code";
	}

	
	//It performs OTP verification operation
	@PostMapping("/verifyCode")
	public String verifyCode(@RequestParam("code") Integer code) {
        //Check entered OTP is correct or not 
		if (code.equals(session.getAttribute("verificationCode"))) { //If OTP matches with actual code
			//Remove verification code from session scope
			session.removeAttribute("verificationCode");
			
			//Verification of the  OTP at the time of user registration process
			if(session.getAttribute("userDetails")!=null) return "redirect:registerUser";
			else {//Verification of the OTP at the time of resetting password
			return "change_password";
			}
		} else {//If OTP not matches with actual code
			//Display message
			Message message = new Message("Invalid verification code entered!", "error", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:enterOTP";
		}
	}
	

	//It performs password reset operation
	@PostMapping("/changePassword")
	public String changeUserPassword(@RequestParam("password") String password) {
		//first encrypt password received password
		String pwd=encoder.encode(password);
		//Call change password method (com.nt.service.UserServiceImpl)
		String result=userService.changePassword((String) session.getAttribute("email"), pwd);
		//Display message
		Message message = new Message(result, "success", "alert-success");
		session.setAttribute("message", message);
		return "redirect:login";
	}

	
	// Perform Category Image saving operation in database
	@PostMapping("/addCategory")
	@PreAuthorize("hasAuthority('admin')")
	public String addCategory(@RequestParam("name") String name, @RequestParam("file") MultipartFile file,
			Map<String, Object> map) {
        //Generate random number upto 100
		Integer random = new Random().nextInt(100);
		//Get original file name from MultipartFile
		String fileName = file.getOriginalFilename();
		//First store file into local folder (com.nt.service.CategoryServiceImpl)
		boolean status = categoryService.storeCategoryImage(file, random);
		  //pass random number + filename 
        
		//Store new Category details & file path in database 
		if (status) {//If file is stored at local level successfully
			//Add new category in database  (com.nt.service.CategoryServiceImpl)
			 status = categoryService.addCategory(name, random + fileName);
		}

		if (status) {//If new category is added successfully
			Message message = new Message("New Category is added successfully!", "success", "alert-success");
			session.setAttribute("message", message);
		} else {//Failed to add new category
			Message message = new Message("Failed to add new  Category!", "error", "alert-danger");
			session.setAttribute("message", message);
		}
		return "redirect:admin";
	}

	
	// It returns admin page
	@GetMapping("/displayAdmin")
	@PreAuthorize("hasAuthority('admin')")
	public String displayAdmin(Map<String, Object> map) {
		//Get all registered admin list (com.nt.service.AdminsServiceImpl)
		List<Admins> adminList= adminService.getAllAdmins();
		//save admin list into map object
		map.put("adminList", adminList);
		return "display_admin";
	}

	
	// It performs admin registration operation
	@PostMapping("/registerAdmin")
	@PreAuthorize("hasAuthority('admin')")
	public String registerAdmin(@ModelAttribute Admins ad, @RequestParam("password") String password) {
		//first encrypt password the received password
		String pwd=encoder.encode(password);
		//Register admin (com.nt.service.AdminsServiceImpl)
		adminService.registerAdmin(ad, pwd);
		return "redirect:displayAdmin";
	}
	

	// It perform admin deletion operation
	@GetMapping("/deleteAdmin")
	@PreAuthorize("hasAuthority('admin')")
	public String deleteAdmin(@RequestParam("id") String id) {
		//Delete the admin account (com.nt.service.AdminsServiceImpl)
		String res = adminService.deleteAdminById(Integer.parseInt(id));
		System.out.println(res);
		return "redirect:displayAdmin";
	}
	

	// It returns page which contains all users information
	@GetMapping("/displayUsers")
	@PreAuthorize("hasAuthority('admin')")
	public String displayAllUsers(Map<String, Object> map) {
		//Get all registered Users(com.nt.service.UsersServiceImpl)
		List<Users> usersList= userService.getAllUsers();
		//Save users list into map object
		map.put("usersList", usersList);
		return "display_users";
	}
	

	// It performs user deletion operation
	@GetMapping("/deleteUser")
	@PreAuthorize("hasAuthority('admin')")
	public String deleteUser(@RequestParam("id") String id) {
		//Delete use account(com.nt.service.UsersServiceImpl)
		userService.deleteUserAccountByUserId(Integer.parseInt(id));
		return "redirect:displayUsers";
	}

	
	// It returns page which shows all available categories
	@GetMapping("/displayCategories")
	@PreAuthorize("hasAuthority('admin')")
	public String dispayAllCategories(Map<String, Object> map) {
		//Get all available category list (com.nt.service.CategoryServiceImpl)
		List<Category> categoryList = categoryService.getAllCategory();
		//save category list into map object
		map.put("categoryList", categoryList);
		return "display_category";
	}
	

	// It performs category deletion operation
	@GetMapping("/deleteCategory")
	@PreAuthorize("hasAuthority('admin')")
	public String deleteCategory(@RequestParam("id") String id) {
		//Delete category (com.nt.service.CategoryServiceImpl)
		categoryService.deleteCategoryById(Integer.parseInt(id));
		return "redirect:displayCategories";
	}
	

	// It returns page for category updation
	@GetMapping("/updateCategory")
	@PreAuthorize("hasAuthority('admin')")
	public String updateCategory(@RequestParam("id") String id, @ModelAttribute("category") Category cat,
			                                                    Map<String, Object> map) {
		//Get category by category id(com.nt.service.CategoryServiceImpl)
		Category category = categoryService.findCategoryById(Integer.parseInt(id));
		//Store category details to model Attribute class Category
		cat.setName(category.getName());
		cat.setFilePath(category.getFilePath());
		cat.setId(category.getId());
		//save category details to map object
		map.put("cat", category);
		return "update_category";
	}

	
	// It performs category updation operation
	@PostMapping("/updateCategory")
	@PreAuthorize("hasAuthority('admin')")
	public String updateCategory(@ModelAttribute("category") Category category,
			                                                   @RequestParam("file") MultipartFile file) {
		//Update Category Details (com.nt.service.CategoryServiceImpl)
		categoryService.updateCategory(category, file);
		return "redirect:displayCategories";
	}
	

	// It perform product addition operation
	@PostMapping("/addProduct")
	@PreAuthorize("hasAuthority('admin')")
	public String addProduct(@ModelAttribute("product") Products product,
			                                         @RequestParam("filePath") MultipartFile file) {
		//Generate random number upto 100
		Integer random = new Random().nextInt(100);
		//Get original file name from MultipartFile object
		String imageName = file.getOriginalFilename();
		//Set image path to product object
		product.setImage(random + imageName);
		//store product image to local folder
		boolean status =productService.storeProductImage(file, random);
      
		if (status) {//If image store at local folder successfully
			//Save product details to database
			status =  productService.addProduct(product);
		}

		if (status) {//If product details saved successfully
			//Display message
			Message message = new Message("New Product is added successfully!", "success", "alert-success");
			session.setAttribute("message", message);
		}else {
			//Display message
			Message message = new Message("Failed to add new product!", "error", "alert-danger");
			session.setAttribute("message", message);
		}
		return "redirect:admin";
	}
	
	
       //Display all products
	@GetMapping("/displayProducts")
	@PreAuthorize("hasAuthority('admin')")
	public String displayProducts(Map<String, Object> map) {
		//Get list of all available products (com.nt.service.ProductServiceImpl)
		List<Products> pro = productService.getAllProducts();
		//Get all Category Types
		HashMap<Integer, String> categoryTypes=categoryService.getAllCategoryTypes();
		
		//Store the product list into map object
		map.put("productList", pro);
		//Store category types list into map object
		map.put("categoryType", categoryTypes);
		return "display_products";
	}
	
	
     //It performs product deletion operation
	@GetMapping("/deleteProduct")
	@PreAuthorize("hasAuthority('admin')")
	public String deleteProduct(@RequestParam("id") String id) {
		//Delete the product(com.nt.service.ProductServiceImpl)
		productService.deleteProductById(Integer.parseInt(id));
		return "redirect:displayProducts";
	}
	

	// It returns page for product updation
	@GetMapping("/updateProduct")
	@PreAuthorize("hasAuthority('admin')")
	public String updateProduct(@RequestParam("id") String id, @ModelAttribute("product") Products pr,
			                                                 Map<String, Object> map) {
		//Get Product Details (com.nt.service.ProductServiceImpl)
		Products product = productService.findProductById(Integer.parseInt(id));
		//Copy properties from the existing product to the new pr object
		BeanUtils.copyProperties(product, pr);
        //Store product details to map object
		map.put("prod", product);
        //Get all Category (com.nt.service.CategoryServiceImpl)
		List<Category> category = categoryService.getAllCategory();
		//Store category list to map object
		map.put("categoryList", category);
		return "update_product";
	}
	

	//It performs product updation operation
	@PostMapping("/updateProduct")
	@PreAuthorize("hasAuthority('admin')")
	public String updateProduct(@ModelAttribute Products product, @RequestParam("filePath") MultipartFile file) {
        //Update product details(com.nt.service.ProductServiceImpl)
		productService.updateProduct(product, file);
		return "redirect:displayProducts";
	}
     
	
	//It displays all orders
	@GetMapping("/displayOrders")
	@PreAuthorize("hasAuthority('admin')")
	public String displayOrders(Map<String, Object> map) {
		//Get order list (com.nt.service.OrderService)
		List<Order> orderList=orderService.getAllOrders();
		//Store order list into map object
		map.put("orderList", orderList);
		
		//Get all order address
		HashMap<Integer, String> userAddress=orderService.getAllOrdersAddress(orderList);
		//Store order address into map object
		map.put("userAddress", userAddress);
		
		return "display_orders";
	}
	

	//It performs order status updation operation
	@PostMapping("/updateOrderStatus")
	@PreAuthorize("hasAuthority('admin')")
	public String updateOrderStatus(@RequestParam("status") String orderStatus, @RequestParam("orderId") String orderId) {
		//update order status (com.nt.service.OrderServiceImpl)
		orderService.updateOrderStatusByOrderId(orderStatus, orderId);
		return "redirect:displayOrders";
	}
     
	
	//It will show user profile
	@GetMapping("/userProfile")
	@PreAuthorize("hasAuthority('user')")
	public String showUserProfile(@ModelAttribute("user") Users storeUserDetails, Map<String, Object> map) {
		// Retrieve the active user object from session scope
		Users user = (Users) session.getAttribute("activeUser");
		// Fetch user details by passing the active user id(com.nt.UserServiceImpl)
		Users userDetails = userService.getUserById(user.getId());
		// Copy user details to storeUserDetails
		// Do not copy 'user' directly to 'storeUserDetails' to ensure immediate reflection of updates
		BeanUtils.copyProperties(userDetails, storeUserDetails);

 
		//Get all wishlist items of active user(com.nt.wishlistServiceImpl)
		List<Products> productList = wishlistService.getAllWishlistProductsByUserId(user.getId());
		//Store wishlist product list to map object
		map.put("wishListProducts", productList);

		//Get all order list of active user(com.nt.OrderServiceImpl)
		List<Order> orderList=orderService.getAllOrdersByUserId(user.getId());
		//Store order list to map object
		map.put("orderList", orderList);

		return "profile";
	}

	
	//It performs active user updation operation
	@PostMapping("/updateUserDetails")
	@PreAuthorize("hasAuthority('user')")
	public String updateUserDetails(@ModelAttribute Users user) {
		//update user details (com.nt.service.UserServiceImpl)
		userService.updateUser(user);
		//Get user details
		Users userDetails=(Users)session.getAttribute("activeUser");
		//Get Updated user details from DB
		Users updatedUserDetails=userService.getUserById(userDetails.getId());
		//Store updated user details into session scope
		session.setAttribute("activeUser", updatedUserDetails);
		return "redirect:userProfile";
	}

	
	//It will display products (All products or perticular Category products)
	@GetMapping("/products")
	public String showProducts(Map<String, Object> map, @RequestParam(name = "category", required = false) String id) {
		if (id == null || id.equals("0")) {	//Display all products
			//Get all available products (com.nt.service.ProductServiceImpl)
			List<Products> list = productService.getAllProducts();
			//Store product list to map object
			map.put("productList", list);

		} else {//Display product of perticular category
			//Get product list of perticular category by category id
			List<Products> list = productService.getAllProductsByCategoryId(Integer.parseInt(id));
			//Store product list to map object
			map.put("productList", list);
		}

		//Get active user object
		Users user = (Users) session.getAttribute("activeUser");
		if(user!=null) { //If active user found
			//Get all wishlist by active user id(com.nt.service.WishlistServiceImpl)
			List<WishList> wishList = wishlistService.getAllWishlistByActiveUserId(user.getId());
            //Declare list to store wishlist product id
			List<Integer> wishListProd = new ArrayList<>();
			//Store product id to 'wishListProd'
			for (WishList wl : wishList) {
				wishListProd.add(wl.getPid());
			}
            
			//Store wishlist product is to map object
			map.put("wishList", wishListProd);
		}
	
		return "products";
	}

	
	//It will return page that will show product details
	@GetMapping("/viewProduct")
	public String viewProduct(@RequestParam("pid") String id, Map<String, Object> map) {
		//Get product by product id (com.nt.service.ProductServiceImpl)
		Products prod = productService.findProductById(Integer.parseInt(id));
		//Get Category type (com.nt.service.CategoryServiceImpl)
		String categoryType=categoryService.findCategoryById(prod.getCategoryType()).getName();
		
		//Store product details to map object
		map.put("product", prod);
		//Store product category type to map object
		map.put("categoryType", categoryType);
		return "viewProduct";
	}

	
	//It will add product to  user cart
	@PostMapping("/addToCart")
	@PreAuthorize("hasAuthority('user')")
	public String addToCart(@RequestParam("userId") Integer uid, @RequestParam("productId") Integer pid,
		                                        	@RequestParam("quantity") Integer quantity) {
		//Add to cart (com.nt.service.CartServiceImpl)
		String result = cartService.saveToCart(uid, pid, quantity);
        //Display message
		Message message = new Message(result, "success", "alert-success");
		session.setAttribute("message", message);
        //redirect to same page
		return "redirect:viewProduct?pid=" + pid;
	}

	
	//show cart items of active user
	@GetMapping("/showCart")
	@PreAuthorize("hasAuthority('user')")
	public String showCart(@RequestParam("uid") String uid, Map<String, Object> map) {
		//Get all cart items of current user (com.nt.service.CartServiceImpl)
		List<Cart> cartList = cartService.getAllCartProductsByActiveUserId(Integer.parseInt(uid));
        
		//calculate total price
		int totalPrice = 0;
		for (Cart cart : cartList) {
			totalPrice += cart.getQuantity() * cart.getProductDetails().getPriceAfterDiscount();
		}
		//Store total price to map object
		map.put("totalPrice", totalPrice);
		
		if (cartList.size() == 0) {//If cart is empty
			map.put("cartList", null);
		} else {
			map.put("cartList", cartList);
		}

		return "cart";
	}

	
	//Remove item from cart 
	@GetMapping("/removeCart")
	@PreAuthorize("hasAuthority('user')")
	public String removeCart(@RequestParam("cartId") String cartId) {
		//Get active user object from session scope
		Users user = (Users) session.getAttribute("activeUser");
		//Remove item by cart id (com.nt.service.CartServiceImpl)
		cartService.removeCartByCartId(Integer.parseInt(cartId.trim()));

		return "redirect:showCart?uid=" + user.getId();
	}

	
	//It execute for each request
	@ModelAttribute
	public void addAttributes(Map<String, Object> map, HttpServletResponse response) {
		// get all categories and save to map
		List<Category> categoryList = categoryService.getAllCategory();
		map.put("categoryList", categoryList);
		try {
			//Get active user object
			Users user = (Users) session.getAttribute("activeUser");
			
			//Check if user active status
			if(!userService.isUserActiveOrNot(user.getId())) { //If status is false
				//remove active user object form session scope
				session.removeAttribute("activeUser");
				// Redirect to login page
				response.sendRedirect("/login?logout");
			}
			
			//Count total items of cart
			int count = 0;
			if (user != null) {
				count = cartService.countTotalCartNoByUserId(user.getId());
			}
			//Store total cart count to map object
			map.put("totalCartCount", count);
		}catch(Exception e) { //If 'activeUser' is not found in session object
                //Catch the exception and do noting
		}
	}

	
	//item or items checkout operation
	@PostMapping("/checkout")
	@PreAuthorize("hasAuthority('user')")
	public String showCheckoutPage(HttpServletRequest request,
			                                                          @RequestParam(name = "quantity", required = false) Integer quantity,
			                                                          @RequestParam(name = "productId", required = false) String pid, Map<String, Object> map) {

		//Retrieve the request URL 
		String referer = request.getHeader("Referer");
		//Retrieve active user object from session object
		Users activeUser = (Users) session.getAttribute("activeUser");

		String from = "";

		//Check if the user is trying to checkout all cart items or a particular item
		if (referer.endsWith("showCart?uid=" + activeUser.getId())) {
			from = "cart";
		} else {
			from = "buy";
		}

	    //Users user = (Users) session.getAttribute("activeUser");
		
        //store 'from' & 'activeUser' into map object
		map.put("from", from);
		map.put("user", activeUser);

		if (from.equals("cart")) {//Checkout all cart items
			//Get all cart product list of user (com.nt.service.CartServiceImpl)
			List<Cart> cartList = cartService.getAllCartProductsByActiveUserId(activeUser.getId());

			//Calculate total price and total items
			int totalPrice = 0;
			int totalProducts = 0;
			for (Cart cart : cartList) {
				totalPrice += cart.getQuantity() * cart.getProductDetails().getPriceAfterDiscount();
				totalProducts = totalProducts + cart.getQuantity();
			}
			
			//Store total items count to map object(com.nt.service.CartServiceImpl)
			map.put("totalItems", cartService.countTotalCartNoByUserId(activeUser.getId()));
			//Store total product to map object
			map.put("totalProducts", totalProducts);
			//Store total price to map object
			map.put("totalPrice", totalPrice);
			
		} else if (from.equals("buy")) {//Checkout perticular item
			//Get product details(com.nt.service.ProductServiceImpl)
			Products prod = productService.findProductById(Integer.parseInt(pid));
			
			//Check products are available in stock or not
			if(prod.getQuantity()==0) {//product is out of stock
				//Display message
				Message message = new Message("Product is currently out of Stock", "error", "alert-danger");
				session.setAttribute("message", message);
				//Redirect to same page
				return "redirect:viewProduct?pid="+pid;
			}
			else if(quantity>prod.getQuantity()) { //Quantity is more than available stock
				//Display message
				Message message = new Message("Only <strong>"+prod.getQuantity()+"</strong> Products are available in Stock", "error", "alert-danger");
				session.setAttribute("message", message);
				//Redirect to same page
				return "redirect:viewProduct?pid="+pid;
			}
			else { //Requested quantity of products are available in stock
            //Store quantity into map object
			map.put("quantity", quantity);
            //Store product price into map object
			map.put("productPrice", prod.getPriceAfterDiscount());
			
			//Store product id & quantity to session scope
			session.setAttribute("buyingProductId", prod.getId());
			session.setAttribute("buyingQuantity", quantity);
			}
		}

		return "checkout";
	}
	

	//It performs order placing operation
	@PostMapping("/placeOrder")
	@PreAuthorize("hasAuthority('user')")
	public String placeOrder(@RequestParam("op") String operation, @RequestParam("paymentMode")String paymentMode) {
		//Get active user id
		Integer userId=((Users)session.getAttribute("activeUser")).getId();

		if(operation.equals("cart")) {//Place all cart items
			//Place all cart items(com.nt.service.OrderServiceImpl)
			HashMap<String, String> hashMap=orderService.placeAllCartOrders(userId, paymentMode);;
            
			if(hashMap.get("status").equals("false")) {// Failed to place order
				//Display message
				Message message = new Message(hashMap.get("message"), "error", "alert-danger");
				session.setAttribute("message", message);
			}else if(hashMap.get("status").equals("true")) {// Order place successfully
				//Store in session scope
				session.setAttribute("order","placed");
			}else { //else
				//Display message
				Message message = new Message("Something Went Wrong", "error", "alert-danger");
				session.setAttribute("message", message);
			}
			return "redirect:showCart?uid="+userId;

		}else if(operation.equals("buy")) {//Place perticular item
            //Get product id & Quantity from session scope
			Integer productId=(Integer) session.getAttribute("buyingProductId");
			Integer quantity=(Integer)session.getAttribute("buyingQuantity");
			//Remove product id & Quantity from session scope
			session.removeAttribute("buyingProductId");
			session.removeAttribute("buyingQuantity");

			//Place order (com.nt.service.OrderServiceImpl)
			HashMap<String, String> hashMap=orderService.placeOrder(userId,productId, quantity, paymentMode);

			if(hashMap.get("status").equals("false")) {//Failed to place order
				//Display message
				Message message = new Message(hashMap.get("message"), "error", "alert-danger");
				session.setAttribute("message", message);	
			}else if(hashMap.get("status").equals("true")) {//Order Placed successfully
				//Store to session scope
				session.setAttribute("order", "placed");
			}else {//else
				//Display message
				Message message = new Message("Something Went Wrong", "error", "alert-danger");
				session.setAttribute("message", message);
			}
			return "redirect:viewProduct?pid="+productId;

		}else //else
			return "redirect:/";
	}

	
	//Wishlist operation (Add to wishlist or remove from wishlist)
	@GetMapping("/wishlist")
	@PreAuthorize("hasAuthority('user')")
	public String performWishListOperation(HttpServletRequest request, @RequestParam("uid") String uid,
			                                                                       @RequestParam("pid") String pid, @RequestParam("op") String operation) {
		//Retrieve the URL of the previous page from the "Referer" header in the HTTP request
		String referer = request.getHeader("Referer");

		if (operation.equals("add")) { //Product add to wishlist
			//Add product to wishlist (com.nt.service.WishlistServiceImpl)
			wishlistService.addWishlistProductByUserId(Integer.parseInt(uid), Integer.parseInt(pid));
		} else if (operation.equals("remove")) {//Remove product from wishlist
			//Remove product from wishlist (com.nt.service.WishlistServiceImpl)
			wishlistService.removeWishlistProductByUserId(Integer.parseInt(uid), Integer.parseInt(pid));
		}
		
		// Redirect to the same page from which the request originated, or to the home page if the referer is null
		return "redirect:" + (referer != null ? referer : "/");
	}

	
	//Return access denied page
	@GetMapping("/accessDenied")
	public String accessDeniedPage() {
		return "accessDenied";
	}

}//End of Main Controller
