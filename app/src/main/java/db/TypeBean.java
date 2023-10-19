package db;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20222022/12/19 11:59
 */

public class TypeBean {
                int id;
                String typename;        //类型名称
                int imageId;                   //未被选中图片
                int sImageId;                 //被选中图片
                int kind;                           //收入-1 支出-0

    public TypeBean() {

    }

    public TypeBean(int id, String typename, int imageId, int simageId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.sImageId = simageId;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSimageId() {
        return sImageId;
    }

    public void setSimageId(int simageId) {
        this.sImageId = simageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}


