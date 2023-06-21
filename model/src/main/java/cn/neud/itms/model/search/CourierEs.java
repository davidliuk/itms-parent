package cn.neud.itms.model.search;

import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.*;

@Data
@Document(indexName = "courier_es" ,shards = 3,replicas = 1)
public class CourierEs {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword, index = false)
    private String takeName;

    //https://blog.csdn.net/zaishijizhidian/article/details/81015988
    @GeoPointField
    private GeoPoint location; //x:经度 y:纬度

    @Field(type = FieldType.Keyword, index = false)
    private String storePath;

    @Field(type = FieldType.Keyword, index = false)
    private String detailAddress;

    @Field(type = FieldType.Double, index = false)
    private Double distance;
}
