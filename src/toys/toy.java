package toys;

public class toy {
    private Integer id;
    private String name;
    private Integer count;
    private Integer weight;
    private String description;


    public toy(Integer id, String name, Integer count, Integer weight, String description) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.weight = weight;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
