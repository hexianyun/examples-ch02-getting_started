package grouping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import backtype.storm.grouping.CustomStreamGrouping;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;

/**
 * Created by hxy on 2017/2/8.
 */
public class ModuleGrouping implements CustomStreamGrouping ,Serializable{

    private  List<Integer> targetTask;
    //这是0.7.1 version的实现
    @Override
    public void prepare(TopologyContext topologyContext, Fields fields, List<Integer> targetTask) {
        System.out.println(" tasks: " + targetTask.toString());
        this.targetTask = targetTask;
    }

    @Override
    public List<Integer> chooseTasks(List<Object> values) {
        System.out.println("number of tasks: " + targetTask.size());
        List<Integer> boltIds = new ArrayList<Integer>();
        if(values.size()>0){
            String str = values.get(0).toString();
            if(str.isEmpty()){
                boltIds.add(targetTask.get(0));
            }else{
                boltIds.add(targetTask.get(str.charAt(0) % targetTask.size()));
            }
        }
        return boltIds;
    }
// 这是0.9.0 version的实现
  /*  @Override
    public void prepare(WorkerTopologyContext workerTopologyContext, GlobalStreamId globalStreamId, List<Integer> targetTask) {
        System.out.println(" tasks: " + targetTask.toString());
        this.targetTask = targetTask;
    }

    @Override
    public List<Integer> chooseTasks(int i, List<Object> values) {
        System.out.println("number of tasks: " + targetTask.size());
        List<Integer> boltIds = new ArrayList<Integer>();
        if(values.size()>0){
            String str = values.get(0).toString();
            if(str.isEmpty()){
                boltIds.add(targetTask.get(0));
            }else{
                boltIds.add(targetTask.get(str.charAt(0) % targetTask.size()));
            }
        }
        return boltIds;
    }*/
}
