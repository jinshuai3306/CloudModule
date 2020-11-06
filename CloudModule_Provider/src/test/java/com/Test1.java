package com;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 21:05
 * description:
 */
public class Test1 {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++){
            boolean r = true;
            for (int j = i-1; j >1 ;j --){

                if (i%j == 0){
                    r = false;
                    break;
                }
            }
            if (r){

            }
        }
    }

}
