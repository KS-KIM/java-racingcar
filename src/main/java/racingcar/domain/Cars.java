package racingcar.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 자동차 목록 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @author HyoChan
 * @since 2020/02/13
 */
public class Cars {
	public static final int MIN_CARS_SIZE = 1;
	public static final int MAX_CARS_SIZE = 10;
	private final List<Car> cars;

	public Cars(final List<Car> cars) {
		validateNotDuplicate(cars);
		validateCarsSize(cars);
		this.cars = Collections.unmodifiableList(cars);
	}

	private void validateNotDuplicate(final List<Car> cars) {
		Set<Car> uniqueCars = new HashSet<>(cars);
		if (cars.size() != uniqueCars.size()) {
			throw new IllegalArgumentException("중복된 이름이 존재합니다.");
		}
	}

	private void validateCarsSize(final List<Car> cars) {
		if (cars.size() < MIN_CARS_SIZE || cars.size() > MAX_CARS_SIZE) {
			throw new IllegalArgumentException("자동차의 수가 범위 밖입니다.");
		}
	}

	public boolean isPositionsOf(List<Integer> positions) {
		validateSize(positions);
		for (int i = 0; i < cars.size(); i++) {
			if (!cars.get(i).isPositionOf(positions.get(i))) {
				return false;
			}
		}
		return true;
	}

	public void moveAll(final RandomNumberGenerator randomNumberGenerator) {
		List<Integer> numbers = randomNumberGenerator.generateRandomNumbers(cars.size());
		moveAll(numbers);
	}

	public void moveAll(List<Integer> powers) {
		validateSize(powers);
		for (int i = 0; i < cars.size(); i++) {
			cars.get(i).move(powers.get(i));
		}
	}

	private void validateSize(List<Integer> input) {
		if (cars.size() != input.size()) {
			throw new IllegalArgumentException("Car 리스트와 입력 리스트의 사이즈가 같지 않습니다.");
		}
	}

	private int calculateMaxPosition() {
		return cars.stream()
				.map(Car::getPosition)
				.map(Position::getPosition)
				.max(Integer::compare)
				.orElseThrow(IllegalArgumentException::new);
	}

	public WinningRule getWinningRule() {
		return new WinningRule(cars, calculateMaxPosition());
	}

	public List<Car> getCars() {
		return cars;
	}
}
