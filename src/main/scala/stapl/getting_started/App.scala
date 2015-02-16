package stapl.getting_started

import stapl.core.pdp._
import stapl.core._

object App {
  
  def main(args : Array[String]) {
	  import ExamplePolicy._
	  val pdp = new PDP(policy)
	  println(pdp.evaluate("subject1", "view", "resource1", 
			  subject.roles -> List("physician"))) // will return Permit
	  println(pdp.evaluate("subject1", "view", "resource1", 
			  subject.roles -> List("another role"))) // will return Deny	  
	  println(pdp.evaluate("subject1", "another action", "resource1", 
			  subject.roles -> List("physician"))) // will return NotApplicable
  }

}

object ExamplePolicy extends BasicPolicy {
	
  import stapl.core.dsl._
  
  subject.roles = ListAttribute(String)

  val policy = Policy("example policy") := when (action.id === "view") apply PermitOverrides to (
      Rule("permit physicians") := permit iff ("physician" in subject.roles),
      Rule("default deny") := deny
  )  
}
