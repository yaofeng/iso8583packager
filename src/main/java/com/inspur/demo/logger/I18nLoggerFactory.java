package com.inspur.demo.logger;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import java.util.Locale;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

public class I18nLoggerFactory {
    
    final static IMessageConveyor mc = new MessageConveyor(Locale.getDefault());
    final static LocLoggerFactory locLoggerFactory = new LocLoggerFactory(mc);
    
    public static LocLogger getLogger(Class<?> clazz) {
        return locLoggerFactory.getLocLogger(clazz);
    }
}
