plugins {
    id("base")
}

tasks.named("assemble") {
    dependsOn(tasks.withType(aggregate.Aggregate::class))
}
