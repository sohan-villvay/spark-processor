package com.villvay.sparkprocessor.schema;

import org.apache.spark.sql.types.StructType;
import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.StringType;
import static org.apache.spark.sql.types.DataTypes.IntegerType;
import static org.apache.spark.sql.types.DataTypes.LongType;

public class FileSchema {

    public static StructType getItemFileSchema(){
        return new  StructType()
                .add("WebActive", StringType)
                .add("Vendor#", IntegerType)
                .add("Material#", StringType)
                .add("SAP#", StringType)
                .add("MFRPart#", StringType)
                .add("Description", StringType)
                .add("UOM", StringType)
                .add("Rank", IntegerType)
                .add("Status", StringType)
                .add("ChangeDate", IntegerType)
                .add("Weight", DoubleType)
                .add("UnitOfWeight", StringType)
                .add("MaterialGroup", StringType)
                .add("MaterialType", StringType)
                .add("Direct", StringType)
                .add("P65Carcenogen", StringType)
                .add("P65Birth", StringType)
                .add("P65Wood", StringType)
                .add("MinimumQuantity", IntegerType)
                .add("QuantityByIncrements", IntegerType)
                .add("BoxQuantity", IntegerType)
                .add("UPCCode", StringType)
                .add("Hazardous", StringType)
                .add("MaterialGroupPackagingMaterial", StringType)
                .add("LaminateSize", StringType)
                .add("LaminateColor", StringType)
                .add("LaminateGrade", StringType)
                .add("LaminateFinish", StringType)
                .add("Document", StringType)
                .add("CreateDate", IntegerType);
    }
}
