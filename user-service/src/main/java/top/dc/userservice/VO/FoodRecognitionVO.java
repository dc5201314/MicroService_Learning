package top.dc.userservice.VO;
import lombok.Data;
import java.util.List;

@Data
public class FoodRecognitionVO {
    private String imageUrl;
    private List<FoodItem> foods;

    @Data
    public static class FoodItem {
        private String name;
        private Double confidence;
        private List<String> category;
        private Double calorie;
    }
}
