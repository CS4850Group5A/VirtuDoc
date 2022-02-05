RSpec.describe 'SSL Proxy' do
  it 'Generates certificate store file' do
    expect(File.exists?('proxy/cert/virtudoc-https.pfx')).to eql true
  end

  it 'Returns 200 when proxying web app' do
    response = RestClient.get("https://localhost/debug/health")
    health_data = JSON.parse(response)
    expect(health_data['health']).to eql 'OK'
  end
end