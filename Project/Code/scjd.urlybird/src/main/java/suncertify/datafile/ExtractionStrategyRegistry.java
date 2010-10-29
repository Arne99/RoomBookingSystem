package suncertify.datafile;

import java.util.HashMap;
import java.util.Map;

class ExtractionStrategyRegistry {

    private static final ExtractionStrategyRegistry INSTANCE = new ExtractionStrategyRegistry();

    private final Map<Integer, SchemaExctractionStrategy> strategies = new HashMap<Integer, SchemaExctractionStrategy>();

    static ExtractionStrategyRegistry instance() {
	return INSTANCE;
    }

    void addStrategy(final SchemaExctractionStrategy strategy) {
	strategies.put(strategy.getSupportedFormatIdentifier(), strategy);
    }

    SchemaExctractionStrategy getStrategyForFormatIdentifier(final int identifier) {
	return strategies.get(identifier);
    }

}
