RSpec.describe 'Web Container' do
  it 'Returns 200 when connected directly through HTTP' do
    response = RestClient.get("http://localhost:8080/debug/health")
    health_data = JSON.parse(response)
    expect(health_data['health']).to eql 'OK'
  end
end
