package $organization$

import org.apache.flink.api.scala._


object Job {
  def main(args: Array[String]): Unit = {
    // set up the execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment

    
    env.execute("Job")
  }
}
