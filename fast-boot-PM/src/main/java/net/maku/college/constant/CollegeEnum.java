package net.maku.college.constant;

public enum CollegeEnum {

    BIGDATA(1,"大数据与互联网学院"),
    SINOGERMAN(2,"中德智能制造学院"),
    URBAN(3,"城市交通与物流学院"),
    MARXISM(4,"马克思主义学院"),
    BUSINESS(5,"商学院"),
    DESIGN(6,"创意设计学院"),
    HEALTH(7,"健康与环境工程学院"),
    MATERIALS(8,"新材料与新能源学院"),
    DRAGON(9,"聚龙学院"),
    ENGINEERING(10,"工程物理学院"),
    FOREIGN(11,"外国语学院"),
    QUALITY(12,"质量和标准学院"),
    MEDICINE(13,"药学院"),
    SPORTS(14,"体育与艺术学院");



    private final int collegeId;

    private final String collegeName;

    private CollegeEnum(int collegeId,String collegeName) {
        this.collegeId = collegeId;
        this.collegeName = collegeName;
    }

    public static String getColName(int collegeId){
        for(CollegeEnum collegeEnum : CollegeEnum.values()){
            if(collegeId == collegeEnum.getId())
                return collegeEnum.getCollegeName();
        }
        return null;
    }

    public String getCollegeName() {
        return this.collegeName;
    }

    public int getId() {
        return this.collegeId;
    }
}
