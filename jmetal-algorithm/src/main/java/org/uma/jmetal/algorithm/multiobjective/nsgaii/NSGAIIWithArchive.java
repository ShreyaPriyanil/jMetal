package org.uma.jmetal.algorithm.multiobjective.nsgaii;

import org.uma.jmetal.component.ranking.Ranking;
import org.uma.jmetal.component.ranking.impl.FastNonDominatedSortRanking;
import org.uma.jmetal.component.termination.Termination;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.archive.Archive;

import java.util.List;

/**
 * Variant of NSGA-II using an external archive. The archive is updated with the evaluated solutions
 * and the solution list it contains is returned as algorithm result.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class NSGAIIWithArchive<S extends Solution<?>> extends NSGAII<S> {
  private Archive<S> archive;
  private int numberOfSolutionsToTakeFromTheArchive;

  /** Constructor */
  public NSGAIIWithArchive(
      Problem<S> problem,
      int populationSize,
      int offspringPopulationSize,
      CrossoverOperator<S> crossoverOperator,
      MutationOperator<S> mutationOperator,
      Termination termination,
      Ranking<S> ranking,
      Archive<S> archive,
      int numberOfSolutionsToTakeFromTheArchive) {
    super(
        problem,
        populationSize,
        offspringPopulationSize,
        crossoverOperator,
        mutationOperator,
        termination,
        ranking);

    this.archive = archive;
    this.numberOfSolutionsToTakeFromTheArchive = numberOfSolutionsToTakeFromTheArchive;
  }

  /** Constructor */
  public NSGAIIWithArchive(
      Problem<S> problem,
      int populationSize,
      int offspringPopulationSize,
      CrossoverOperator<S> crossoverOperator,
      MutationOperator<S> mutationOperator,
      Termination termination,
      Archive<S> archive,
      int numberOfSolutionsToTakeFromTheArchive) {
    this(
        problem,
        populationSize,
        offspringPopulationSize,
        crossoverOperator,
        mutationOperator,
        termination,
        new FastNonDominatedSortRanking<>(),
        archive,
        numberOfSolutionsToTakeFromTheArchive);
  }

  @Override
  protected List<S> evaluatePopulation(List<S> solutionList) {
    List<S> evaluatedSolutionList = super.evaluatePopulation(solutionList);
    for (S solution : evaluatedSolutionList) {
      archive.add(solution);
    }

    return evaluatedSolutionList;
  }

  @Override
  public List<S> getResult() {
    return SolutionListUtils.distanceBasedSubsetSelection(
        archive.getSolutionList(), numberOfSolutionsToTakeFromTheArchive);
  }

  public Archive<S> getArchive() {
    return archive;
  }
}
