Exploration of OpenTelemetry
===============================

# What?
 - OpenTelemetry: It's a collection of tools, APIs, and SDKs which are used
for instrumenting, generating, and collecting an application performance 
data (metrics, logs, and traces).

- Jaeger: Used for storing and viewing OpenTelemetry traces.

- Prometheus: Used for storing OpenTelemetry metrics.

- Grafana: Used for viewing metrics.

# Why?
- We use New Relic for collecting application performance data (metrics, traces).

- We will be ending our contract with New Relic in the next few months.

- Exploring if OpenTelemetry and Jaeger along with Prometheus can be a substitute

# What is accomplished
- Instrument application with OpenTelemetry agent.

- Collect trace data and view it in Jaeger 

# Work Remaining
- Set up OpenTelemetry collector, Jaeger, and Prometheus to collect metrics.

# Challenge
- Probably having an unified and smart UI like New Relic

- Set up new frameworks
