package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class InMemoryTimeEntryRepository  implements TimeEntryRepository{
    private List<TimeEntry> inMemoryRepo = null;
    private Long incrementor = 0L;

    public InMemoryTimeEntryRepository(){
        this.inMemoryRepo = new ArrayList<>();
        this.incrementor = 0L;
    }
    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        if(timeEntry != null)
            timeEntry.setId(++incrementor);
        inMemoryRepo.add(timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long timeEntryId) {
        for(TimeEntry timeEntry : inMemoryRepo){
            if(timeEntry.getId() == timeEntryId)
                return timeEntry;
        }
        return null;
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> expected = asList(
                new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8),
                new TimeEntry(2L, 789L, 654L, LocalDate.parse("2017-01-07"), 4)
        );
        return inMemoryRepo;
    }

    @Override
    public TimeEntry update(Long timeEntryId, TimeEntry timeEntry) {
        TimeEntry existingEntry = find(timeEntryId);
        if(existingEntry == null) {
            return null;
        }
        existingEntry.setUserId(timeEntry.getUserId());
        existingEntry.setProjectId(timeEntry.getProjectId());
        existingEntry.setDate(timeEntry.getDate());
        existingEntry.setHours(timeEntry.getHours());
        return existingEntry;
    }

    @Override
    public void delete(Long timeEntryId) {
        inMemoryRepo.remove(find(timeEntryId));
    }
}
