from locust import HttpLocust, TaskSet, task, constant

class ApiTaskSet(TaskSet):

    @task
    def loadtest(self):
        response = self.client.get("/api")

class LoadTest(HttpLocust):
    task_set = ApiTaskSet
    wait_time = constant(0)
