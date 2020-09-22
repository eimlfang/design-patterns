package victor.training.oo.structural.proxy;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

interface IExpensiveOps {
	Boolean isPrime(int n);
	String hashAllFiles(File folder);
}
//Decorator Pattern:
class ExpensiveOpsCuLog implements  IExpensiveOps{
	private final IExpensiveOps ops;

	ExpensiveOpsCuLog(IExpensiveOps ops) {
		this.ops = ops;
	}

	@Override
	public Boolean isPrime(int n) {
		return null;
	}
	@Override
	public String hashAllFiles(File folder) {
		return null;
	}
}
class ExpensiveOpsCuCache implements  IExpensiveOps{
	private final IExpensiveOps delegate;
	private final Map<Integer, Boolean> cache = new HashMap<>();
	private final Map<File, String> cache2 = new HashMap<>();

	ExpensiveOpsCuCache(IExpensiveOps delegate) {
		this.delegate = delegate;
	}
	public Boolean isPrime(int n) {
		if (cache.containsKey(n)){
			return cache.get(n);
		}
		Boolean prime = delegate.isPrime(n);
		cache.put(n, prime);
		return prime;
	}
	@Override
	public String hashAllFiles(File folder) {
		if (cache2.containsKey(folder)){
			return cache2.get(folder);
		}
		String hash = delegate.hashAllFiles(folder);
		cache2.put(folder, hash);
		return hash;
	}
}
@Service
@Slf4j
public class ExpensiveOps /*implements IExpensiveOps*/{

	private static final BigDecimal TWO = new BigDecimal("2");

	@Cacheable("primes")
	public Boolean isPrime(int n) {
		log.debug("Computing isPrime({})", n);
		BigDecimal number = new BigDecimal(n);
		if (number.compareTo(TWO) <= 0) {
			return true;
		}
		if (number.remainder(TWO).equals(BigDecimal.ZERO)) {
			return false;
		}
		for (BigDecimal divisor = new BigDecimal("3");
			  divisor.compareTo(number.divide(TWO)) < 0;
			  divisor = divisor.add(TWO)) {
			if (number.remainder(divisor).equals(BigDecimal.ZERO)) {
				return false;
			}
		}
		return true;
	}

	@Cacheable("folders")
	@SneakyThrows
	public String hashAllFiles(File folder) {
		log.debug("Computing hashAllFiles({})", folder);
		MessageDigest md = MessageDigest.getInstance("MD5");
		for (int i = 0; i < 3; i++) { // pretend there is much more work to do here
			Files.walk(folder.toPath())
				.map(Path::toFile)
				.filter(File::isFile)
				.map(Unchecked.function(FileUtils::readFileToString))
				.forEach(s -> md.update(s.getBytes()));
		}
		byte[] digest = md.digest();
		return DatatypeConverter.printHexBinary(digest).toUpperCase();
	}

	@CacheEvict("folders")
	public void scotDinCacheFolder(File folder) {
		// MAGIC! Do NOT DELETE. Let the magic happen !
	}
}
