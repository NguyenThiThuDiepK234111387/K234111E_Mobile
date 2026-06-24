package com.NguyenThiThuDiep.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//just sample.....
public class DataWareHouse {
    public static ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        Category c1 = new Category("c1", "Rau Xanh - Củ quả");
        Category c2 = new Category("c2", "Dầu ăn thực vật");
        Category c3 = new Category("c3", "Nước sốt");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        return categories;
    }

    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Category> categories = getCategories();

        // Danh mục 1: Rau Xanh - Củ quả (40 items)
        String[] rauNames = {
                "Bắp cải xanh", "Súp lơ trắng", "Cà rốt Đà Lạt", "Khoai tây bi", "Ớt chuông đỏ",
                "Dưa leo baby", "Cà chua bi", "Xà lách mỡ", "Cải ngọt", "Mướp đắng",
                "Bầu sao", "Bí đao", "Cà tím tròn", "Đậu cô ve", "Đậu bắp",
                "Giá đỗ sạch", "Nấm kim châm", "Nấm đùi gà", "Rau dền", "Rau mồng tơi",
                "Rau ngót", "Cần tây Mỹ", "Tỏi Lý Sơn", "Hành lá", "Gừng già",
                "Sả cây", "Ngò rí", "Khoai lang mật", "Khổ qua rừng", "Rau muống xanh",
                "Cải bẹ xanh", "Cải thìa", "Đậu Hà Lan", "Ngô ngọt", "Củ cải trắng",
                "Củ năng", "Khoai môn", "Măng tây", "Bí đỏ", "Rau lang"
        };
        for (int i = 0; i < 40; i++) {
            String id = "p" + (i + 1);
            String name = rauNames[i % rauNames.length] + (i >= rauNames.length ? " loại " + (i / rauNames.length + 1) : "");
            double price = 10000 + (i * 500);
            int qty = 20 + (i % 30);
            products.add(new Product(id, name, price, qty, 0.05, 0.1, categories.get(0).getCategoryId()));
        }

        // Danh mục 2: Dầu ăn thực vật (30 items)
        String[] dauNames = {
                "Dầu đậu nành Simply", "Dầu Meizan Gold", "Dầu Tường An Cooking", "Dầu Neptune Light",
                "Dầu Oliu Extra Virgin", "Dầu mè thơm Kiddy", "Dầu lạc nguyên chất", "Dầu dừa tinh luyện",
                "Dầu hướng dương Simply", "Dầu gạo lứt Simply", "Dầu hạt cải Maggi", "Bơ thực vật Tường An",
                "Dầu Marvela", "Dầu ăn Cái Lân", "Dầu đậu nành Tiara", "Dầu Oliu Borges",
                "Dầu hạt lanh", "Dầu hạt óc chó", "Dầu quả bơ", "Dầu Gấc",
                "Dầu Meizan Đậu nành", "Dầu Tường An Premium", "Dầu Neptune Gold", "Dầu Simply Gạo",
                "Dầu hướng dương Nga", "Dầu Oliu Pomace", "Dầu mè Nakydaco", "Dầu dừa Vietcoco",
                "Dầu hạt thông", "Dầu hạnh nhân"
        };
        for (int i = 0; i < 30; i++) {
            String id = "p" + (i + 41);
            String name = dauNames[i % dauNames.length] + (i >= dauNames.length ? " - " + (i + 1) : "");
            double price = 30000 + (i * 1500);
            int qty = 10 + (i % 20);
            products.add(new Product(id, name, price, qty, 0.02, 0.1, categories.get(1).getCategoryId()));
        }

        // Danh mục 3: Nước sốt (30 items)
        String[] sotNames = {
                "Tương ớt Chinsu", "Tương cà Cholimex", "Nước mắm Nam Ngư", "Nước tương Maggi",
                "Dầu hào Cholimex", "Sốt Mayonnaise Aji-mayo", "Sốt BBQ truyền thống", "Sốt mè rang Kewpie",
                "Nước mắm Chin-su Cá Hồi", "Sốt Teriyaki Nhật", "Sốt phô mai cay", "Tương đen ăn phở",
                "Sốt Spaghetti", "Sốt lẩu Thái", "Sốt dầu giấm", "Sốt ướp thịt nướng",
                "Sốt Curry Ấn Độ", "Tương hột", "Sốt mù tạt vàng", "Nước mắm Phú Quốc",
                "Nước tương Tam Thái Tử", "Tương ớt siêu cay", "Sốt Salad Nga", "Dầu giấm táo",
                "Sốt Kim chi", "Sốt cà chua đậm đặc", "Sốt nấm", "Sốt tiêu đen",
                "Sốt chanh dây", "Nước màu dừa"
        };
        for (int i = 0; i < 30; i++) {
            String id = "p" + (i + 71);
            String name = sotNames[i % sotNames.length] + (i >= sotNames.length ? " Spec " + (i + 1) : "");
            double price = 15000 + (i * 1200);
            int qty = 30 + (i % 40);
            products.add(new Product(id, name, price, qty, 0, 0.1, categories.get(2).getCategoryId()));
        }

        return products;
    }
    public static Product downloadProduct(int i)
    {
        ArrayList<Product>products=getProducts();
        if(i<0 || i>=products.size())
            return null;
        return products.get(i);
    }

    public static ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("C001", "Trần Duy Thanh", "0981234567", "thanh.tran@gmail.com", "TP. Hồ Chí Minh"));
        customers.add(new Customer("C002", "Nguyễn Văn Tèo", "0901122334", "teo.nguyen@yahoo.com", "TP. Hà Nội"));
        customers.add(new Customer("C003", "Lê Thị Tý", "0912233445", "ty.le@hotmail.com", "TP. Đà Nẵng"));
        customers.add(new Customer("C004", "Phạm Hồng Phúc", "0943344556", "phuc.pham@outlook.com", "TP. Huế"));
        customers.add(new Customer("C005", "Hoàng Minh Quân", "0934455667", "quan.hoang@gmail.com", "TP. Hải Phòng"));
        customers.add(new Customer("C006", "Vũ Tuyết Mai", "0975566778", "mai.vu@gmail.com", "TP. Cần Thơ"));
        customers.add(new Customer("C007", "Đặng Quang Hùng", "0966677889", "hung.dang@gmail.com", "Bắc Ninh"));
        customers.add(new Customer("C008", "Bùi Bích Phương", "0927788990", "phuong.bui@gmail.com", "Quảng Ninh"));
        customers.add(new Customer("C009", "Ngô Gia Bảo", "0918899001", "bao.ngo@gmail.com", "Nghệ An"));
        customers.add(new Customer("C010", "Đỗ Kim Liên", "0959900112", "lien.do@gmail.com", "Thanh Hóa"));
        return customers;
    }

    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new Employee("E01", "Nguyễn Trúc Linh", "0904747901", "Kon Tum"));
        employees.add(new Employee("E02", "Trần Minh Khang", "0912345678", "TP. Hồ Chí Minh"));
        employees.add(new Employee("E03", "Lê Hoài An", "0987654321", "TP. Đà Nẵng"));
        employees.add(new Employee("E04", "Phạm Gia Hân", "0934567890", "TP. Cần Thơ"));
        employees.add(new Employee("E05", "Võ Nhật Nam", "0966332211", "Đồng Nai"));

        return employees;
    }

    public static ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        try {
            orders.add(new Order("O01", "C001", "E01", sdf.parse("20/04/2026 08:30"),OrderStatus.COMPLETED));
            orders.add(new Order("O02", "C002", "E02", sdf.parse("20/04/2026 09:15"),OrderStatus.COMPLETED));
            orders.add(new Order("O03", "C003", "E03", sdf.parse("20/05/2026 10:05"),OrderStatus.CUSTOMER_COMPLAIN));
            orders.add(new Order("O04", "C004", "E04", sdf.parse("22/04/2026 11:20"),OrderStatus.GOING_LOGISTIC));
            orders.add(new Order("O05", "C005", "E05", sdf.parse("20/05/2026 13:10"),OrderStatus.COMPLETED));
            orders.add(new Order("O06", "C006", "E01", sdf.parse("23/05/2026 14:25"),OrderStatus.NOT_YET_PAYMENT));
            orders.add(new Order("O07", "C007", "E02", sdf.parse("23/04/2026 15:40"),OrderStatus.NOT_YET_PAYMENT));
            orders.add(new Order("O08", "C008", "E03", sdf.parse("23/05/2026 16:30"),OrderStatus.COMPLETED));
            orders.add(new Order("O09", "C009", "E04", sdf.parse("28/05/2026 18:00"),OrderStatus.COMPLETED));
            orders.add(new Order("O10", "C010", "E05", sdf.parse("28/05/2026 19:15"),OrderStatus.GOING_LOGISTIC));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static ArrayList<OrderDetail> getOrderDetails() {
        ArrayList<OrderDetail> details = new ArrayList<>();

        // Giả sử Coupon và VAT là tỷ lệ (ví dụ: 0.2 = 20%, 0.1 = 10%)
        details.add(new OrderDetail("OD001", "O01", "p1", 1, 25900, 0.2, 0.1));
        details.add(new OrderDetail("OD002", "O01", "p2", 2, 13500, 0, 0.1));
        details.add(new OrderDetail("OD003", "O01", "p3", 1, 12500, 0, 0.1));
        details.add(new OrderDetail("OD004", "O01", "p4", 1, 12000, 0, 0.1));
        details.add(new OrderDetail("OD005", "O01", "p5", 1, 18000, 0, 0.1));

        details.add(new OrderDetail("OD006", "O02", "p1", 1, 25900, 0.2, 0.1));
        details.add(new OrderDetail("OD007", "O02", "p3", 2, 12500, 0, 0.1));
        details.add(new OrderDetail("OD008", "O02", "p5", 1, 18000, 0, 0.1));
        details.add(new OrderDetail("OD009", "O02", "p6", 1, 22000, 0, 0.1));
        details.add(new OrderDetail("OD010", "O02", "p9", 1, 89000, 0.05, 0.1));

        details.add(new OrderDetail("OD011", "O03", "p2", 1, 13500, 0, 0.1));
        details.add(new OrderDetail("OD012", "O03", "p4", 2, 12000, 0, 0.1));
        details.add(new OrderDetail("OD013", "O03", "p6", 1, 22000, 0, 0.1));
        details.add(new OrderDetail("OD014", "O03", "p7", 1, 35000, 0, 0.1));
        details.add(new OrderDetail("OD015", "O03", "p8", 2, 18000, 0, 0.1));
        details.add(new OrderDetail("OD016", "O03", "p9", 1, 89000, 0.05, 0.1));

        details.add(new OrderDetail("OD017", "O04", "p1", 2, 25900, 0.2, 0.1));
        details.add(new OrderDetail("OD018", "O04", "p2", 1, 13500, 0, 0.1));
        details.add(new OrderDetail("OD019", "O04", "p3", 1, 12500, 0, 0.1));
        details.add(new OrderDetail("OD020", "O04", "p6", 2, 22000, 0, 0.1));
        details.add(new OrderDetail("OD021", "O04", "p8", 1, 18000, 0, 0.1));

        details.add(new OrderDetail("OD022", "O05", "p4", 1, 12000, 0, 0.1));
        details.add(new OrderDetail("OD023", "O05", "p5", 2, 18000, 0, 0.1));
        details.add(new OrderDetail("OD024", "O05", "p6", 1, 22000, 0, 0.1));
        details.add(new OrderDetail("OD025", "O05", "p7", 1, 35000, 0, 0.1));
        details.add(new OrderDetail("OD026", "O05", "p9", 1, 89000, 0.05, 0.1));

        details.add(new OrderDetail("OD027", "O06", "p1", 1, 25900, 0.2, 0.1));
        details.add(new OrderDetail("OD028", "O06", "p2", 1, 13500, 0, 0.1));
        details.add(new OrderDetail("OD029", "O06", "p3", 2, 12500, 0, 0.1));
        details.add(new OrderDetail("OD030", "O06", "p4", 1, 12000, 0, 0.1));
        details.add(new OrderDetail("OD031", "O06", "p5", 1, 18000, 0, 0.1));
        details.add(new OrderDetail("OD032", "O06", "p8", 2, 18000, 0, 0.1));

        details.add(new OrderDetail("OD033", "O07", "p2", 2, 13500, 0, 0.1));
        details.add(new OrderDetail("OD034", "O07", "p4", 1, 12000, 0, 0.1));
        details.add(new OrderDetail("OD035", "O07", "p5", 1, 18000, 0, 0.1));
        details.add(new OrderDetail("OD036", "O07", "p6", 1, 22000, 0, 0.1));
        details.add(new OrderDetail("OD037", "O07", "p7", 1, 35000, 0, 0.1));

        details.add(new OrderDetail("OD038", "O08", "p1", 1, 25900, 0.2, 0.1));
        details.add(new OrderDetail("OD039", "O08", "p3", 1, 12500, 0, 0.1));
        details.add(new OrderDetail("OD040", "O08", "p5", 2, 18000, 0, 0.1));
        details.add(new OrderDetail("OD041", "O08", "p8", 1, 18000, 0, 0.1));
        details.add(new OrderDetail("OD042", "O08", "p9", 1, 89000, 0.05, 0.1));

        details.add(new OrderDetail("OD043", "O09", "p1", 2, 25900, 0.2, 0.1));
        details.add(new OrderDetail("OD044", "O09", "p2", 2, 13500, 0, 0.1));
        details.add(new OrderDetail("OD045", "O09", "p4", 1, 12000, 0, 0.1));
        details.add(new OrderDetail("OD046", "O09", "p6", 1, 22000, 0, 0.1));
        details.add(new OrderDetail("OD047", "O09", "p7", 1, 35000, 0, 0.1));
        details.add(new OrderDetail("OD048", "O09", "p8", 1, 18000, 0, 0.1));

        details.add(new OrderDetail("OD049", "O10", "p3", 2, 12500, 0, 0.1));
        details.add(new OrderDetail("OD050", "O10", "p4", 1, 12000, 0, 0.1));
        details.add(new OrderDetail("OD051", "O10", "p5", 1, 18000, 0, 0.1));
        details.add(new OrderDetail("OD052", "O10", "p6", 2, 22000, 0, 0.1));
        details.add(new OrderDetail("OD053", "O10", "p9", 1, 89000, 0.05, 0.1));

        return details;
    }

    public static double sumOfValueOrder(Order od) {
        double sum = 0;
        ArrayList<OrderDetail> details = getOrderDetails();
        for (OrderDetail detail : details) {
            if (detail.getOrderId().equalsIgnoreCase(od.getOrderId())) {
                // Thành tiền cơ bản
                double baseAmount = detail.getQuantity() * detail.getPrice();

                // Tính số tiền được giảm (Coupon là tỷ lệ, ví dụ 0.1 = 10%)
                double discountAmount = baseAmount * detail.getCoupon();

                // Giá trị sau khi chiết khấu
                double amountAfterDiscount = baseAmount - discountAmount;

                // Tính tiền thuế VAT trên giá trị đã giảm (VAT là tỷ lệ, ví dụ 0.08 = 8%)
                double vatAmount = amountAfterDiscount * detail.getVAT();

                // Tổng giá trị cuối cùng của dòng chi tiết này
                double finalLineAmount = amountAfterDiscount + vatAmount;

                sum += finalLineAmount;
            }
        }
        return sum;
    }
    public static ArrayList<Order> filterOrdersByDate(Date fromDate, Date toDate) {
        ArrayList<Order> orders = getOrders();
        ArrayList<Order> result = new ArrayList<>();
        for (Order order : orders) {
            Date orderDate = order.getOrderDate();
            // Kiểm tra nếu ngày đơn hàng nằm trong khoảng từ fromDate đến toDate (bao gồm cả 2 đầu)
            if (orderDate != null && !orderDate.before(fromDate) && !orderDate.after(toDate)) {
                result.add(order);
            }
        }
        return result;
    }
    public static ArrayList<Order>filterOrderByStatus(OrderStatus status)
    {
        ArrayList<Order> orders=getOrders();
        if(status==OrderStatus.ALL)
            return orders;
        ArrayList<Order>result=new ArrayList<>();
        for (Order order:orders)
        {
            if (order.getOrderStatus()==status)
            {
                result.add(order);
            }
        }
        return result;
    }
    public static ArrayList<Order> filterOrderByStatusAndDate(OrderStatus status, Date fromDate, Date toDate) {
        ArrayList<Order> orders = getOrders();
        ArrayList<Order> result = new ArrayList<>();
        for (Order order : orders) {
            // Lọc theo trạng thái (nếu là ALL thì chấp nhận tất cả)
            boolean statusMatch = (status == OrderStatus.ALL || order.getOrderStatus() == status);

            // Lọc theo khoảng thời gian [fromDate, toDate]
            Date orderDate = order.getOrderDate();
            boolean dateMatch = (orderDate != null && !orderDate.before(fromDate) && !orderDate.after(toDate));

            if (statusMatch && dateMatch) {
                result.add(order);
            }
        }
        return result;
    }
}
