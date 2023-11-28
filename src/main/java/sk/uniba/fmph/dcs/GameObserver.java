package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.ObserverInterface;

import java.util.ArrayList;
import java.util.List;

public class GameObserver implements ObserverInterface {
  private List<ObserverInterface> observers;

  public GameObserver() {

    observers = new ArrayList<>();
  }

  public void registerObserver(ObserverInterface observer) {

    if (!observers.contains(observer)) {

      observers.add(observer);
    }
  }

  public void cancelObserver(ObserverInterface observer) {

    observers.remove(observer);
  }

  @Override
  public void notify(String state) {

    for (ObserverInterface observer : observers) {

      observer.notify(state);
    }
  }
}
