package com.ckj.projects;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * created by ChenKaiJu on 2018/8/29  11:57
 */
public class FileUtils{

        public static List<String> getAllAbslutefilePaths(File file){
            List<String>paths=new LinkedList<>();
            getAllAbslutefilePaths(paths,file);
            return paths;
        }

        public static List<String> getAllChildFiles(File file){
            List<String> paths=new LinkedList<>();
            getAllFilePaths(paths,"",file);
            return paths;
        }


        private static void getAllFilePaths(List<String> paths,String parent,File file){
            if(file.isFile()){
                String endName=parent+"/"+file.getName();
                paths.add(endName);
                return;
            }else{
                parent=parent+"/"+file.getName();
                File[]files=file.listFiles();
                for(File file1:files){
                    getAllFilePaths(paths,parent,file1);
                }
            }

        }

        private static void getAllAbslutefilePaths(List<String> paths,File file){
            if(file.isFile()){
                paths.add(file.getAbsolutePath());
                return;
            }else{
                File[]files=file.listFiles();
                for(File file1:files){
                    getAllAbslutefilePaths(paths,file1);
                }
            }
        }

}
