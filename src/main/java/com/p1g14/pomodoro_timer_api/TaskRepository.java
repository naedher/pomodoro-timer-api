package com.p1g14.pomodoro_timer_api;

import org.springframework.data.jpa.repository.JpaRepository;

// Jpa brings quite a lot of flexibility for database schema, we basically fetch data from database
// like we fetch from an internal list,array etc. using a spesific repository class for each table.
// this is magical, i will explain later
public interface TaskRepository extends JpaRepository<Tasks,Long> {

}
