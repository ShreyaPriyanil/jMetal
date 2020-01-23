package org.uma.jmetal.problem.singleobjective;


import org.apache.commons.text.similarity.LevenshteinDistance;
import org.uma.jmetal.problem.sequenceproblem.impl.CharSequenceProblem;
import org.uma.jmetal.solution.stringsolution.impl.CharSequenceSolution;
import org.uma.jmetal.util.checking.Check;

/**
*/
@SuppressWarnings("serial")
public class StringMatching extends CharSequenceProblem {
  private String targetString;
  LevenshteinDistance distance = new LevenshteinDistance();

  public StringMatching(String targetString) {
    this.targetString = targetString ;

    setNumberOfVariables(targetString.length());
    setNumberOfObjectives(1);
    setNumberOfConstraints(0);
    setName("String Match");
  }

  @Override
  public int getLength() {
    return targetString.length();
  }

  @Override
  public void evaluate(CharSequenceSolution solution) {
    Check.that(solution.getLength() == targetString.length(), "The solution has an invalid length");
     int counter = 0 ;
     String string = "" ;
     for (int i = 0; i < getLength(); i++) {
      string += solution.getVariable(i) ;
     }

     counter = distance.apply(targetString, solution.getVariables().toString()) ;
    //for (int i = 0 ; i < targetString.length(); i++) {
       //if (targetString.charAt(i) != solution.getVariable(i)) {
       //  counter ++ ;
       //}
       //counter += Math.abs(targetString.charAt(i) - solution.getVariable(i)) ;
       //counter += distance.apply(targetString, solution.getVariables().toString()) ;
     //}

    solution.setObjective(0, counter);
  }
}