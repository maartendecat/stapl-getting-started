package stapl.getting_started

import stapl.core.pdp._

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

import stapl.core._
import stapl.core.templates._

object ExamplePolicy extends BasicPolicy {
  
  subject.roles = ListAttribute(String)

  val policy = Policy("example policy") := when (action.id === "view") apply PermitOverrides to (
      Rule("permit physicians") := when ("physician" in subject.roles) permit,
      defaultDeny
  )  
}
