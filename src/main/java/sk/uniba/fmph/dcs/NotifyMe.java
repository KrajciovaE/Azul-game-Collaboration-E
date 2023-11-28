package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.ObserverInterface;

public class NotifyMe implements ObserverInterface {
    @Override
    public void notify(String newState) {
        System.out.println(newState);
    }
}
