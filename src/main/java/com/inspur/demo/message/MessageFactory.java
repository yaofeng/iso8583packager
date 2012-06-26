package com.inspur.demo.message;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import java.util.Locale;

public class MessageFactory {
    
    final static IMessageConveyor mc = new MessageConveyor(Locale.getDefault());
    
    public static <E extends Enum<?>> String getMessage(E e, Object... args) {
        return mc.getMessage(e, args);
    }
}
