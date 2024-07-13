package com.daviaNhat.osahaneat.service;

import com.daviaNhat.osahaneat.entity.Category;
import com.daviaNhat.osahaneat.entity.Food;
import com.daviaNhat.osahaneat.repository.FoodRepository;
import com.daviaNhat.osahaneat.service.imp.FileServiceImp;
import com.daviaNhat.osahaneat.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MenuService implements MenuServiceImp {

    @Autowired
    FileServiceImp fileServiceImp;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public boolean createMenu(MultipartFile file, String title, boolean is_freeship, String time_ship, double price, int cat_id) {
        boolean isInsertSuccess = false;
        try{
            boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
            if(isSaveFileSuccess){
                Food food = new Food();
                food.setTitle(title);
                food.setImage(file.getOriginalFilename());
                food.setTimeShip(time_ship);
                food.setPrice(price);

                Category category = new Category();
                category.setId(cat_id);
                food.setCategory(category);

                foodRepository.save(food);
                isInsertSuccess = true;
            }
        } catch (Exception e){
            System.out.println("Error insert restaurant" + e.getMessage());
        }
        return isInsertSuccess;
    }
}
