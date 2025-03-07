package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.FinalPointsCalculationInterface;

import java.util.List;
import java.util.Optional;

public class FinalPointsCalculation implements FinalPointsCalculationInterface {
    @Override
    public Points getPoints(List<List<Optional<Tile>>> wall){
        Horizontal horizontalLineRule = new Horizontal();
        Vertical verticalLineRule = new Vertical();
        Color fullColorRule = new Color();
        int sum = 0;
        sum += horizontalLineRule.calculate(wall)+verticalLineRule.calculate(wall)+fullColorRule.calculate(wall);

        return new Points(sum);
    }

}