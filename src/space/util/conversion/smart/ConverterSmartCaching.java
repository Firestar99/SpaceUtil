package space.util.conversion.smart;

import space.util.conversion.ConverterMap;
import space.util.conversion.IConverter;
import spaceOld.engine.logger.Logger;

public class ConverterSmartCaching<MIN> implements IConverterSmart<MIN> {
	
	public ConverterSmart<MIN> converterSmart;
	public ConverterMap<MIN, MIN> convCache = new ConverterMap<>();
	
	public ConverterSmartCaching() {
	}
	
	public ConverterSmartCaching(ConverterSmart<MIN> converterSmart) {
		this.converterSmart = converterSmart;
	}
	
	//delegate
	//convert
	@Override
	public MIN convertNew(MIN from) throws UnsupportedOperationException {
		return converterSmart.convertNew(from);
	}
	
	@Override
	public <LTO extends MIN> LTO convertInstance(MIN from, LTO ret) {
		return converterSmart.convertInstance(from, ret);
	}
	
	@Override
	public MIN convertType(MIN from, Class<? extends MIN> type) {
		return converterSmart.convertType(from, type);
	}
	
	//set
	@Override
	public void setDefaultConversionMethod(ConverterSmartDefaultConversionMethod method) {
		converterSmart.setDefaultConversionMethod(method);
	}
	
	@Override
	public void setResolveSmartIgnoreDuplicatePriorities(boolean resolveSmartIgnoreDuplicatePriorities) {
		converterSmart.setResolveSmartIgnoreDuplicatePriorities(resolveSmartIgnoreDuplicatePriorities);
	}
	
	@Override
	public void setResolveSmartLogger(Logger resolveSmartLogger) {
		converterSmart.setResolveSmartLogger(resolveSmartLogger);
	}
	
	//converters
	@Override
	public <LFROM extends MIN, LTO extends MIN> ConverterSmart.ConverterSmartPriorityConverter<LFROM, LTO> getConverter(Class<LFROM> classFrom, Class<LTO> classTo) {
		return converterSmart.getConverter(classFrom, classTo);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, ConverterSmart.ConverterSmartPriorityConverter<LFROM, LTO> conv) {
		converterSmart.putConverter(classFrom, classTo, conv);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, IConverter<LFROM, LTO> conv, int weight) {
		converterSmart.putConverter(classFrom, classTo, conv, weight);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> IConverter<LFROM, LTO> getConverterDefaultMethod(Class<LFROM> classFrom, Class<LTO> classTo) {
		return converterSmart.getConverterDefaultMethod(classFrom, classTo);
	}
	
	//implement
	@Override
	public <LFROM extends MIN, LTO extends MIN> IConverter<LFROM, LTO> getSmart(Class<LFROM> classFrom, Class<LTO> classTo) throws IllegalStateException {
		IConverter<LFROM, LTO> conv = convCache.get(classFrom, classTo);
		if (conv != null)
			return conv;
		
		conv = converterSmart.getSmart(classFrom, classTo);
		convCache.put(classFrom, classTo, conv);
		return conv;
	}
	
	@Override
	public void clearCache() {
		convCache.clear();
		converterSmart.clearCache();
	}
}