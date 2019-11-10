package com.hgh.homeworksystem.schedule;

import com.hgh.homeworksystem.dto.HomeworkDto;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.service.HomeworkRequestService;
import com.hgh.homeworksystem.service.HomeworkService;
import com.hgh.homeworksystem.util.SimHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;

@Configuration
public class HomeworkScheduler {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HomeworkRequestService homeworkRequestService;

    private static final int DIFF = 10;

    @Scheduled(cron = "0 0/5 * * * ?")
    private void homeworkJob(){
        List<HomeworkRequest> homeworkRequestList = homeworkRequestService.getByStateAndDeadLine();
        for (HomeworkRequest homeworkRequest : homeworkRequestList){
            homeworkRequest.setState(1);
            List<Homework> homeworks = homeworkService.getByRequestId(homeworkRequest.getId());
            if(homeworks == null || homeworks.isEmpty()){
                continue;
            }
            StringBuilder simHomeworkIds = new StringBuilder();
            for(int i=0; i<homeworks.size()-1; i++){
                for(int j=i+1;j<homeworks.size(); j++){
                    List<BigInteger> list1 = SimHashUtil.subByDistance(homeworks.get(i).getSimhash(),DIFF);
                    List<BigInteger> list2 = SimHashUtil.subByDistance(homeworks.get(j).getSimhash(),DIFF);
                    if(isSimilar(list1,list2)){
                        simHomeworkIds.append(homeworks.get(i).getId()+",");
                        simHomeworkIds.append(homeworks.get(j).getId()+",");
                    }
                }
            }
            String ids = simHomeworkIds.toString();
            if(ids.length()>0){
                ids = ids.substring(0,ids.length()-1);
            }
            homeworkRequest.setSimHomework(ids);
        }
        homeworkRequestService.saveAll(homeworkRequestList);
    }


    private static boolean isSimilar(List<BigInteger> list1, List<BigInteger> list2){
        if(list1==null || list2==null){
            if(list1 == null && list2 == null){
                return true;
            }else{
                return false;
            }
        }
        for(int i = 0; i<list1.size(); i++){
            for(int j = 0; j < list2.size(); j++){
                if(list1.get(i).equals(list2.get(j))){
                    return true;
                }
            }
        }
        return false;
    }
}
