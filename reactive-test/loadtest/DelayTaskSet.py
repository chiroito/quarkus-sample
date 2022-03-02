from locust import HttpLocust, TaskSet, task, constant

class DelayTaskSet(TaskSet):

    @task
    def loadtest(self):
        response = self.client.get("/delay")

class LoadTest(HttpLocust):
    task_set = DelayTaskSet
    wait_time = constant(0)
