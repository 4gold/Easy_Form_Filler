package com.socio.test;

import com.socio.Icontroller.ISourceSetting;
import com.socio.Icontroller.ITemplateSetting;
import com.socio.controller.SourceSetting;
import com.socio.controller.TemplateSetting;

/**
 * 
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// test SourceLoader
    	String fileName = "/Users/liuyixin/Downloads/STSC016/9 金石堂/金石堂_群學2月新品提報(20230208).xlsx";
        ISourceSetting basic = new SourceSetting();
        basic.getAllSourceData();
        basic.addSourceInfo("id", "1");
        basic.addSourceInfo("name", "2");
        basic.addSourceInfo("內容簡介", "000", false);
        
        System.out.println(basic.getAllSourceData().get("name"));
        
        //basic.cleanAllSourceInfo();
        
        // test TemplateSetting
        String tempfileName = "金石堂";
        String templateFileType = ".xlsx";
        //TemplateSetting.uploadTemplate(fileName, tempfileName, templateFileType);
        ITemplateSetting kingstone = new TemplateSetting(tempfileName, templateFileType);
        kingstone.loadTemplateInfo();
        kingstone.addTemplateCellData("內容簡介", 5, 2);
        kingstone.addTemplateCellData("name", 5, "B");
        kingstone.loadSourceData2Cache();
        kingstone.writeJson();
        
        kingstone.writeExcelFile("/Users/liuyixin/Downloads/");
    }
    
 
}
