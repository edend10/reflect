package mirror.bathroom.dao;

import java.time.LocalDateTime;
import java.util.List;

public interface BathroomSittingDao {
    void insertBathroomSitting(BathroomSittingDto sitting);

    List<BathroomSittingDto> getSittingsInRange(LocalDateTime startTime, LocalDateTime endTime);
}
