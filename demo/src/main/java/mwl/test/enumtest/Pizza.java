package mwl.test.enumtest;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mawenlong
 * @date 2020-06-11 16:44
 * <p>
 * 枚举变量可以看成枚举类的实例
 */
public class Pizza {

    private static EnumSet<PizzaStatus> undeliveredPizzaStatuses = EnumSet.of(PizzaStatus.ORDER, PizzaStatus.DELIVERD);
    private PizzaStatus status;

    public enum PizzaStatus {
        ORDER(5) {
            @Override
            public boolean isOrdered() {
                return true;
            }
        },
        READY(2) {
            @Override
            public boolean isReady() {
                return true;
            }
        },
        DELIVERD(0) {
            @Override
            public boolean isDeliverd() {
                return true;
            }
        };

        private int timeToDelivery;

        PizzaStatus(int timeToDelivery) {
            this.timeToDelivery = timeToDelivery;
        }

        public boolean isOrdered() {
            return false;
        }

        public boolean isReady() {
            return false;
        }

        public boolean isDeliverd() {
            return false;
        }
    }

    public static List<Pizza> getAllUndeliveredPizzas(List<Pizza> input) {
        return input.stream().filter((s) -> undeliveredPizzaStatuses.contains(s.status)).collect(Collectors.toList());
    }

}
