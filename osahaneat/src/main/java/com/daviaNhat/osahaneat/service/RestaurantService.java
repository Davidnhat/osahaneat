package com.daviaNhat.osahaneat.service;

import com.daviaNhat.osahaneat.dto.CategoryDTO;
import com.daviaNhat.osahaneat.dto.MenuDTO;
import com.daviaNhat.osahaneat.dto.RestaurantDTO;
import com.daviaNhat.osahaneat.entity.Food;
import com.daviaNhat.osahaneat.entity.MenuRestaurant;
import com.daviaNhat.osahaneat.entity.RatingRestaurant;
import com.daviaNhat.osahaneat.entity.Restaurant;
import com.daviaNhat.osahaneat.repository.RestaurantRepository;
import com.daviaNhat.osahaneat.service.imp.FileServiceImp;
import com.daviaNhat.osahaneat.service.imp.RestaurantServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RestaurantService implements RestaurantServiceImp {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FileServiceImp fileServiceImp;

    @Override
    public boolean insertRestaurant(MultipartFile file, String title, String subtitle, String description, boolean is_freeship, String address, String open_date) {
        boolean isInsertSuccess = false;
        try{
            boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
            if(isSaveFileSuccess){
                Restaurant restaurant = new Restaurant();
                restaurant.setTitle(title);
                restaurant.setSubtitle(subtitle);
                restaurant.setDescription(description);
                restaurant.setImage(file.getOriginalFilename());
                restaurant.setFreeship(is_freeship);
                restaurant.setAddress(address);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date openDate = simpleDateFormat.parse(open_date);
                restaurant.setOpenDate(openDate);

                restaurantRepository.save(restaurant);
                isInsertSuccess = true;
            }
        } catch (Exception e){
            System.out.println("Error insert restaurant" + e.getMessage());
        }
        return isInsertSuccess;
    }

    @Override
    public List<RestaurantDTO> getHomePageRestaurant() {
        List<RestaurantDTO> restaurantDTOS = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0,6); //yêu cầu xem lấy bao nhiêu dữ liệu
        Page<Restaurant> listData = restaurantRepository.findAll(pageRequest);
        for (Restaurant data : listData) {
            RestaurantDTO restaurantDTO =new RestaurantDTO();
            restaurantDTO.setId(data.getId());
            restaurantDTO.setImage(data.getImage());
            restaurantDTO.setTitle(data.getTitle());
            restaurantDTO.setSubTitle(data.getSubtitle());
            restaurantDTO.setFreeShip(data.isFreeship());
            restaurantDTO.setRating(calculatorRating(data.getListRatingRestaurant())); //do có kết nối với bảng rating nên query được luôn bảng reting

            restaurantDTOS.add(restaurantDTO);
        }

        return restaurantDTOS;
    }

    @Override
    public RestaurantDTO getDetailRestaurant(int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        RestaurantDTO restaurantDTO = new RestaurantDTO();

        //kiểm tra restaurant có null hay không
        if(restaurant.isPresent()){
            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            Restaurant data = restaurant.get(); //.get: để hủy optional đi về lại entity

            restaurantDTO.setTitle(data.getTitle());
            restaurantDTO.setSubTitle(data.getSubtitle());
            restaurantDTO.setImage(data.getImage());
            restaurantDTO.setRating(calculatorRating(data.getListRatingRestaurant()));
            restaurantDTO.setFreeShip(data.isFreeship());
            restaurantDTO.setDesc(data.getDescription());
            restaurantDTO.setOpenDate(data.getOpenDate());

            //category
            for (MenuRestaurant menuRestaurant : data.getListMenuRestaurant()) {
                List<MenuDTO> menuDTOList = new ArrayList<>();
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setName(menuRestaurant.getCategory().getNameCate());

                //menu
                for (Food food : menuRestaurant.getCategory().getListFood()) {
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setId(food.getId());
                    menuDTO.setImage(food.getImage());
                    menuDTO.setFreeShip(food.isFreeShip());
                    menuDTO.setTitle(food.getTitle());
                    menuDTO.setDesc(food.getDesc());
                    menuDTO.setPrice(food.getPrice());

                    menuDTOList.add(menuDTO);
                }
                categoryDTO.setMenus(menuDTOList);
                categoryDTOList.add(categoryDTO);
            }
            restaurantDTO.setCategories(categoryDTOList);
        }
        return restaurantDTO;
    }

    //Hàm đánh giá
    private double calculatorRating(Set<RatingRestaurant> listRating){
        double totalPoint = 0;
        for (RatingRestaurant data : listRating) {
            totalPoint += data.getRatePoint(); //tổng số point đã đánh giá
        }
        return totalPoint/listRating.size(); //trung bình đánh giá = tổng point/lượt đánh giá
    }
}

